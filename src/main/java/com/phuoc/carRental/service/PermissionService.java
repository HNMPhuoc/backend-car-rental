package com.phuoc.carRental.service;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.PermissionRequest;
import com.phuoc.carRental.dto.responses.PermissionResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.PermissionMapper;
import com.phuoc.carRental.model.Permission;
import com.phuoc.carRental.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    public void create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
    }

    @Transactional(readOnly = true)
    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PermissionResponse getById(UUID permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        return permissionMapper.toPermissionResponse(permission);
    }

    @Transactional
    public void update(UUID permissionId, PermissionRequest request) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionMapper.updatePermission(permission, request);
    }


    public void delete(UUID permissionId) {
        if (!permissionRepository.existsById(permissionId)) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
        permissionRepository.deleteById(permissionId);
    }
}
