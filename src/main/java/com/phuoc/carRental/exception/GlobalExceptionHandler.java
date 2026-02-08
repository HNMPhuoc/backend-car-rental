package com.phuoc.carRental.exception;

import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.responses.ApiCustomResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final String MIN_ATTRIBUTE = "min";

        @ExceptionHandler(ResourceAccessException.class)
        public ResponseEntity<ApiCustomResponse> handleResourceAccess(ResourceAccessException ex) {

                ErrorCode errorCode = ErrorCode.HANDLE_TOO_LONG;

                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(HttpStatusCodeException.class)
        public ResponseEntity<ApiCustomResponse> handleDownstream(HttpStatusCodeException ex) {

                ErrorCode errorCode;

                if (ex.getStatusCode().is5xxServerError()) {
                        errorCode = ErrorCode.BAD_GATEWAY;
                } else {
                        errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
                }

                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(AsyncRequestTimeoutException.class)
        public ResponseEntity<ApiCustomResponse> handleAsyncTimeout() {

                ErrorCode errorCode = ErrorCode.HANDLE_TOO_LONG;

                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(value = AppException.class)
        ResponseEntity<ApiCustomResponse> handlingAppException(AppException exception) {
                ErrorCode errorCode = exception.getErrorCode();
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(value = JwtException.class)
        ResponseEntity<ApiCustomResponse> handlingJwtException(JwtException exception) {
                ErrorCode errorCode = ErrorCode.INVALID_TOKEN;
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(value = AuthenticationServiceException.class)
        ResponseEntity<ApiCustomResponse> handlingAuthenticationServiceException(
                        AuthenticationServiceException exception) {
                ErrorCode errorCode = ErrorCode.INVALID_TOKEN;
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(value = ParseException.class)
        ResponseEntity<ApiCustomResponse> handlingParseException(ParseException exception) {
                ErrorCode errorCode = ErrorCode.INVALID_TOKEN;
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(value = RuntimeException.class)
        public ResponseEntity<ApiCustomResponse> handlingRuntimeException(RuntimeException exception) {
                ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiCustomResponse> handlingValidation(MethodArgumentNotValidException exception) {
                String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

                ErrorCode errorCode = ErrorCode.INVALID_KEY;
                Map<String, Object> attributes = null;
                try {
                        errorCode = ErrorCode.valueOf(enumKey);

                        ConstraintViolation<?> violation = exception.getBindingResult()
                                        .getAllErrors()
                                        .getFirst()
                                        .unwrap(ConstraintViolation.class);

                        attributes = violation.getConstraintDescriptor().getAttributes();
                } catch (IllegalArgumentException ignored) {

                }
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiCustomResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(
                                                                Objects.nonNull(attributes)
                                                                                ? mapAttribute(errorCode.getMessage(),
                                                                                                attributes)
                                                                                : errorCode.getMessage())
                                                .build());
        }

        private String mapAttribute(String message, Map<String, Object> attributes) {
                return message.replace("{min}", String.valueOf(attributes.get(MIN_ATTRIBUTE)));
        }
}
