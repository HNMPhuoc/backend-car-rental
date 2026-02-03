package com.phuoc.carRental.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentityCardResponse {

    UUID id;
    String cccd;
    String fullName;
    String gender;
    LocalDate dob;
    String birthPlace;
    String resAddr;
    String nationally;
    String urlImage;

    UUID userId;
}
