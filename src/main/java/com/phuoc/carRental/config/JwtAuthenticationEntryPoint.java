package com.phuoc.carRental.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        String message = errorCode.getMessage();

        if (authException.getCause() instanceof BadJwtException) {
            message = "Token is expired or invalid";
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiCustomResponse<?> apiResponse = ApiCustomResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        new ObjectMapper().writeValue(response.getWriter(), apiResponse);
    }
}