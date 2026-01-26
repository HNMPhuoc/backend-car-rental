package com.phuoc.carRental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class identityCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String cccd;
    String fullName;
    String gender;
    LocalDate dob;
    String birthPlace;
    String resAddr;
    String nationally;
    String urlImage;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    User user;
}
