package com.phuoc.carRental.common.helpers;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.model.Role;
import com.phuoc.carRental.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleHelper {

    private final RoleRepository roleRepository;

    public Role resolveRole(String roleName) {
        String finalRole = (roleName == null || roleName.isBlank())
                ? "USER"
                : roleName;

        return roleRepository.findByName(finalRole)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }
}

