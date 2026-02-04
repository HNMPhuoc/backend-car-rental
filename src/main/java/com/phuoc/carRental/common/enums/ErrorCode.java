package com.phuoc.carRental.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998, "Invalid message key", HttpStatus.BAD_REQUEST),
    HANDLE_TOO_LONG(9997, "Request processing timeout", HttpStatus.REQUEST_TIMEOUT),
    BAD_GATEWAY(9996, "Bad gateway", HttpStatus.BAD_GATEWAY),
    SERVICE_UNAVAILABLE(9995, "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE),


    //Validation kiểm tra user tồn tại
    USER_EXISTED(4000, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(4044, "User not existed", HttpStatus.NOT_FOUND),

    // Validation cho email
    EMAIL_REQUIRED(4001, "Email is required", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(4002, "Invalid email format", HttpStatus.BAD_REQUEST),

    // Validation cho username
    USERNAME_REQUIRED(4003, "Username is required", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(4004, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),

    // Validation cho password
    PASSWORD_REQUIRED(4005, "Password is required", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4006, "Password must be contain at least 1 uppercase, 1 lowercase, 1 digit, 1 special character", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_LENGTH(4007, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),

    // Validation cho phone
    PHONE_REQUIRED(4008, "Phone number is required", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(4009, "Phone number must contain only digits", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_LENGTH(4010, "Phone number must be at least {min} digits", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(4011, "Phone number existed", HttpStatus.BAD_REQUEST),

    //Validation cho permission, role
    PERMISSION_NAME_REQUIRED(4012, "The permission name must not be left blank", HttpStatus.BAD_REQUEST),
    ROLE_NAME_REQUIRED(4013, "The role name must not be left blank.", HttpStatus.BAD_REQUEST),
    DESCRIPTION_TOO_LONG(4014, "The description exceeds the allowed length", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(4015, "Permission not existed", HttpStatus.NOT_FOUND),
    INVALID_ROLE_NAME(4016, "The role name is invalid", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(4017, "Role not existed", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(4018, "Role existed", HttpStatus.CONFLICT),

    //Validation cho area
    AREA_NAME_REQUIRED(4019, "The area name must not be left blank.", HttpStatus.BAD_REQUEST),
    AREA_NOT_EXISTED(4020, "The rea not existed", HttpStatus.NOT_FOUND),

    //Validation cho car brand
    CAR_BRAND_NAME_REQUIRED(4021, "The brand name must not be left blank", HttpStatus.BAD_REQUEST),
    CAR_BRAND_NOT_EXISTED(4022, "The brand not existed", HttpStatus.BAD_REQUEST),
    CAR_BRAND_EXISTED(4023, "The brand existed", HttpStatus.BAD_REQUEST),

    //Validation cho car model
    CAR_MODEL_NAME_REQUIRED(4024, "The model name must not be left blank", HttpStatus.BAD_REQUEST),
    BRAND_ID_REQUIRED(4025, "The brand id must not be left blank", HttpStatus.BAD_REQUEST),
    CAR_MODEL_NOT_EXISTED(4025, "The model car not existed", HttpStatus.BAD_REQUEST),
    CAR_MODEL_EXISTED(4025, "The model car existed", HttpStatus.BAD_REQUEST),

    //Validation cho car type
    CAR_TYPE_NAME_REQUIRED(4026, "The type name must not be left blank", HttpStatus.BAD_REQUEST),
    MODEL_ID_REQUIRED(4027, "The model id must not be left blank", HttpStatus.BAD_REQUEST),
    CAR_TYPE_EXISTED(4027, "The car type existed", HttpStatus.BAD_REQUEST),
    CAR_TYPE_NOT_EXISTED(4027, "The car type not existed", HttpStatus.BAD_REQUEST),

    //Validation cho cccd
    INVALID_DOB(4028, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    CCCD_REQUIRED(4029, "The CCCD must not be left blank", HttpStatus.BAD_REQUEST),
    FULL_NAME_REQUIRED(4030, "The full name must not be left blank", HttpStatus.BAD_REQUEST),
    USER_ID_REQUIRED(4031, "The user id must not be left blank", HttpStatus.BAD_REQUEST),
    IDENTITY_CARD_EXISTED(4032, "The identity card existed", HttpStatus.BAD_REQUEST),
    IDENTITY_CARD_NOT_EXISTED(4033, "The identity card not existed", HttpStatus.BAD_REQUEST),
    USER_HAS_IDENTITY(4033, "The user already has identity", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(4034, "Upload image cccd already has identity", HttpStatus.BAD_REQUEST),
    OCR_FAILED(4035, "Image cccd scan failed", HttpStatus.BAD_REQUEST),
    IDENTITY_CARD_IMAGE_NOT_EXISTED(4036, "Image cccd not existed", HttpStatus.BAD_REQUEST),
    OCR_EMPTY_RESULT(4036, "The ocr result is empty", HttpStatus.BAD_REQUEST),
    INVALID_CCCD_FORMAT(4036, "No. CCCD is wrong format", HttpStatus.BAD_REQUEST),
    DOB_REQUIRED(4036, "Date of birth is null", HttpStatus.BAD_REQUEST),
    INVALID_GENDER(4036, "Gender data is invalid", HttpStatus.BAD_REQUEST),
    INVALID_NATIONALITY(4036, "Nationality data is invalid", HttpStatus.BAD_REQUEST),
    
    //Validation cho payment method
    PAYMENT_METHOD_NOT_EXISTED(4037, "Payment method not existed", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
