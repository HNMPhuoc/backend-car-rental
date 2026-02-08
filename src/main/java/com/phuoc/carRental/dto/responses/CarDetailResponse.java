package com.phuoc.carRental.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDetailResponse {
    UUID id;
    String licensePlate;
    int manuYear;
    int numSeat;
    String fuelType;
    double fuelCons;
    double price;
    boolean maintStatus;
    boolean browseStatus;
    boolean hide;
    String thumbnail;
}
