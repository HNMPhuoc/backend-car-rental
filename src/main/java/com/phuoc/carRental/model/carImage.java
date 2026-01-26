package com.phuoc.carRental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class carImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String urlImage;

    @ManyToOne
    @JoinColumn(name = "carId")
    Car car;
}
