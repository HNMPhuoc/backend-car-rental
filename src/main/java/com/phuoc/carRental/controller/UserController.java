package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.userAddRequest;
import com.phuoc.carRental.dto.responses.ApiResponse;
import com.phuoc.carRental.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User Controller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @Operation(summary = "Api add new user", description = "thêm user mới với đầu vào body json userAddReq")
    @PostMapping("/add-user")
    public ApiResponse<String> createUser(@RequestBody @Valid userAddRequest request) {
        userService.createUser(request);
        return ApiResponse.<String>builder()
                .code(2000).message("Create user successfully")
                .build();
    }

}
