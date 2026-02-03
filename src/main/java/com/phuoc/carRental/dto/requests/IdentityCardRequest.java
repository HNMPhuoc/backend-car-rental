package com.phuoc.carRental.dto.requests;

import com.phuoc.carRental.common.utils.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentityCardRequest {

    @NotBlank(message = "CCCD_REQUIRED")
    String cccd;

    @NotBlank(message = "FULL_NAME_REQUIRED")
    String fullName;

    String gender;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    String birthPlace;
    String resAddr;
    String nationally;

    @NotNull(message = "USER_ID_REQUIRED")
    UUID userId;
}
