package com.phuoc.carRental.controller;

import com.phuoc.carRental.dto.requests.IntrospectRequest;
import com.phuoc.carRental.dto.requests.LoginRequest;
import com.phuoc.carRental.dto.requests.SignUpRequest;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import com.phuoc.carRental.dto.responses.IntrospectResponse;
import com.phuoc.carRental.dto.responses.LoginResponse;
import com.phuoc.carRental.dto.responses.UserProfileResponse;
import com.phuoc.carRental.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ApiCustomResponse<Void> signUp(@RequestBody SignUpRequest request) {
        authenticationService.signUp(request);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("User registered successfully")
                .build();
    }


    @PostMapping("/login")
    public ApiCustomResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return ApiCustomResponse.<LoginResponse>builder()
                .code(2000)
                .data(authenticationService.signIn(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiCustomResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        try {
            return ApiCustomResponse.<IntrospectResponse>builder()
                    .code(2000)
                    .data(authenticationService.introspect(request))
                    .build();
        } catch (Exception e) {
            return ApiCustomResponse.<IntrospectResponse>builder()
                    .code(4000)
                    .data(IntrospectResponse.builder().valid(false).build())
                    .build();
        }
    }


    @PostMapping("/logout")
    public ApiCustomResponse<Void> logout(Authentication authentication, @RequestHeader("Authorization") String authHeader) {
        String username = authentication.getName();
        String token = authHeader.replace("Bearer ", "");
        authenticationService.signOut(username, token);
        return ApiCustomResponse.<Void>builder()
                .code(2000)
                .message("Logged out successfully")
                .build();
    }


    @GetMapping("/profile")
    public ApiCustomResponse<UserProfileResponse> getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ApiCustomResponse.<UserProfileResponse>builder()
                .code(2000)
                .data(authenticationService.getProfile(username))
                .build();
    }
}
