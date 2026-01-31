package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.common.helpers.RoleHelper;
import com.phuoc.carRental.dto.requests.UserAddRequest;
import com.phuoc.carRental.dto.requests.UserEditRequest;
import com.phuoc.carRental.dto.responses.UserListResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.UserMapper;
import com.phuoc.carRental.model.Role;
import com.phuoc.carRental.model.User;
import com.phuoc.carRental.repository.RoleRepository;
import com.phuoc.carRental.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleHelper roleHelper;

    public void createUser(UserAddRequest req) {
        if (userRepository.existsByEmailOrUsernameOrPhoneNum(
                req.getEmail(),
                req.getUsername(),
                req.getPhoneNum()
        )) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toCreateEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        Set<Role> roles = Optional.ofNullable(req.getRoleId())
                .filter(ids -> !ids.isEmpty())
                .map(ids -> new HashSet<>(roleRepository.findAllById(ids)))
                .orElseGet(HashSet::new);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UUID userId, UserEditRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.toUpdateEntity(user, req);
        Optional.ofNullable(req.getRoleId())
                .ifPresent(roleIds -> {
                    Set<Role> roles = roleIds.isEmpty()
                            ? new HashSet<>()
                            : new HashSet<>(roleRepository.findAllById(roleIds));
                    user.setRoles(roles);
                });
    }

    @Transactional(readOnly = true)
    public UserListResponse getById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserListResponse(user);
    }

    @Transactional(readOnly = true)
    public UserListResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserListResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserListResponse> getUsers(
            Pageable pageable,
            String roleName,
            Boolean online,
            Boolean lock
    ) {
        // default role = USER
        Role role = roleHelper.resolveRole(roleName);

        return userRepository
                .findByRoleAndOnlineAndLockOptional(
                        role,
                        online,
                        lock,
                        pageable
                )
                .map(userMapper::toUserListResponse);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(userId);
    }
}
