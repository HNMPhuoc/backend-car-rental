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
public class CarCreateRequest {

    @NotBlank
    String licensePlate;

    int manuYear;
    int numSeat;
    String fuelType;
    double fuelCons;
    double price;

    @NotNull
    UUID areaId;

    @NotNull
    UUID typeId;
}
