package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.userAddRequest;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.UserMapper;
import com.phuoc.carRental.model.User;
import com.phuoc.carRental.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public void createUser(userAddRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.existsByPhoneNum(req.getPhoneNum())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        User user = userMapper.toEntity(req);
        user.setRoles(new HashSet<>());
        userRepository.save(user);
    }


}
