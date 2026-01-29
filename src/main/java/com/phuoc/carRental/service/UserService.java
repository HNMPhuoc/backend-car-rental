package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.UserAddRequest;
import com.phuoc.carRental.dto.requests.UserEditRequest;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.UserMapper;
import com.phuoc.carRental.model.User;
import com.phuoc.carRental.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public void createUser(UserAddRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.existsByPhoneNum(req.getPhoneNum())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        User user = userMapper.toCreateEntity(req);
        user.setRoles(new HashSet<>());
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UUID userId, UserEditRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.toUpdateEntity(user, req);
    }
}
