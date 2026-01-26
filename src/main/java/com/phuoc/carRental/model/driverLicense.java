package com.phuoc.carRental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class driverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true,nullable = false)
    String dlNumber;

    String rank;
    String urlImage;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    User user;
}
