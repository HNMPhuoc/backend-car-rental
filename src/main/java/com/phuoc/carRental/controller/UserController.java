package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.UserAddRequest;
import com.phuoc.carRental.dto.requests.UserEditRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.UserListResponse;
import com.phuoc.carRental.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@Tag(
        name = "User Controller",
        description = "API quản lý User dành cho Admin"
)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @Operation(
            summary = "Tạo mới user",
            description = "Admin tạo mới user, kiểm tra trùng email/username/phone và gán danh sách role theo roleId"
    )
    @PostMapping
    public ApiCustomResponse<String> createUser(@RequestBody @Valid UserAddRequest request) {
        userService.createUser(request);
        return ApiCustomResponse.<String>builder()
                .code(2000).message("Create user successfully")
                .build();
    }

    @Operation(
            summary = "Lấy user theo ID",
            description = "Admin lấy thông tin chi tiết user theo userId"
    )
    @GetMapping("/{id}")
    public ApiCustomResponse<UserListResponse> getById(@PathVariable UUID id) {
        return ApiCustomResponse.<UserListResponse>builder()
                .code(2000)
                .data(userService.getById(id))
                .build();
    }

    @Operation(
            summary = "Lấy user theo username",
            description = "Admin tìm user theo username"
    )
    @GetMapping("/username/{username}")
    public ApiCustomResponse<UserListResponse> getByUsername(@PathVariable String username) {
        return ApiCustomResponse.<UserListResponse>builder()
                .code(2000)
                .data(userService.getByUsername(username))
                .build();
    }

    @Operation(
            summary = "Cập nhật user",
            description = "Admin cập nhật thông tin user (email, username, phone, role). Chỉ update các field được truyền"
    )
    @PatchMapping("/{userId}")
    public ApiCustomResponse<String> updateUser(@PathVariable("userId") UUID userId, @RequestBody @Valid UserEditRequest request) {
        userService.updateUser(userId, request);
        return ApiCustomResponse.<String>builder()
                .code(2000).message("Update user successfully")
                .build();
    }

    @Operation(
            summary = "Danh sách user phân trang",
            description = """
                    Lấy danh sách user theo phân trang.
                    Filter mặc định:
                    - role = USER
                    - online = null
                    - lock = null
                    """
    )
    @GetMapping
    public ApiCustomResponse<Page<UserListResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean online,
            @RequestParam(required = false) Boolean lock
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiCustomResponse.<Page<UserListResponse>>builder()
                .code(2000)
                .data(userService.getUsers(pageable, online, lock))
                .build();
    }

    @Operation(
            summary = "Xóa user",
            description = "Admin xóa user theo userId"
    )
    @DeleteMapping("/{id}")
    public ApiCustomResponse<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ApiCustomResponse.<String>builder()
                .code(2000)
                .message("Delete user successfully")
                .build();
    }
}
