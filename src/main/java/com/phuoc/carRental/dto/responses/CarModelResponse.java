package com.phuoc.carRental.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarModelResponse {

    UUID id;
    String name;
    UUID brandId;
    String brandName;
}
