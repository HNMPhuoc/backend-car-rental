package com.phuoc.carRental.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarBrandRequest {

    @NotBlank(message = "CAR_BRAND_NAME_REQUIRED")
    String name;
}

