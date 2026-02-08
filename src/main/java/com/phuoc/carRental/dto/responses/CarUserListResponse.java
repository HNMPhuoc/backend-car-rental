package com.phuoc.carRental.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarUserListResponse {
    UUID id;
    String licensePlate;
    int manuYear;
    String fuelType;
    String thumbnail;
    boolean maintStatus;
    boolean browseStatus;
    boolean hide;
}
