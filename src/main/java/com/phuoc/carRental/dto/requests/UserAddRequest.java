package com.phuoc.carRental.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAddRequest {
    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL")
    String email;

    @NotBlank(message = "USERNAME_REQUIRED")
    @Size(min = 5, max = 10, message = "INVALID_USERNAME")
    String username;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 8, max = 30, message = "INVALID_PASSWORD_LENGTH")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,30}$",
            message = "INVALID_PASSWORD"
    )
    String password;

    @NotBlank(message = "PHONE_REQUIRED")
    @Size(min = 9, max = 10, message = "INVALID_PHONE_LENGTH")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "INVALID_PHONE_NUMBER"
    )
    String phoneNum;

    Set<UUID> roleId;
}
