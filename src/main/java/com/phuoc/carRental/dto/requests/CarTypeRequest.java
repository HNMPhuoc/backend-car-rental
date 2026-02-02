package com.phuoc.carRental.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarTypeRequest {

    @NotBlank(message = "CAR_TYPE_NAME_REQUIRED")
    String name;

    @NotNull(message = "MODEL_ID_REQUIRED")
    UUID modelId;
}
