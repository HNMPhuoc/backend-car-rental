package com.phuoc.carRental.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEditRequest {

    @Email(message = "INVALID_EMAIL")
    @Pattern(
            regexp = "^(?!\\s*$).+",
            message = "EMAIL_REQUIRED"
    )
    String email;

    @Size(min = 5, max = 10, message = "INVALID_USERNAME")
    @Pattern(
            regexp = "^(?!\\s*$).{5,10}$",
            message = "USERNAME_REQUIRED"
    )
    String username;

    @Size(min = 9, max = 10, message = "INVALID_PHONE_LENGTH")
    @Pattern(
            regexp = "^(?!\\s*$)[0-9]{9,10}$",
            message = "INVALID_PHONE_NUMBER"
    )
    String phoneNum;
}
