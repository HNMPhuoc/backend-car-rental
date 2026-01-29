package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.UserAddRequest;
import com.phuoc.carRental.dto.requests.UserEditRequest;
import com.phuoc.carRental.dto.responses.ApiResponse;
import com.phuoc.carRental.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User Controller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @Operation(summary = "Api add new user", description = "thêm user mới với đầu vào body json")
    @PostMapping("/add-user")
    public ApiResponse<String> createUser(@RequestBody @Valid UserAddRequest request) {
        userService.createUser(request);
        return ApiResponse.<String>builder()
                .code(2000).message("Create user successfully")
                .build();
    }

    @Operation(summary = "Api update user", description = "cập nhật user với đầu vào id của user và body json")
    @PatchMapping("/update-user/{userId}")
    public ApiResponse<String> updateUser(@PathVariable("userId") UUID userId, @RequestBody @Valid UserEditRequest request) {
        userService.updateUser(userId, request);
        return ApiResponse.<String>builder()
                .code(2000).message("Update user successfully")
                .build();
    }
}
