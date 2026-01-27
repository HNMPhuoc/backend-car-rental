package com.phuoc.carRental.model;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String licensePlate;

    int manuYear;
    int numSeat;
    String thumbnail;
    boolean maintStatus;
    String fuelType;
    double fuelCons;
    double price;

    LocalDate insuranceDate;
    boolean insuranceExpired;
    double insuranceCost;

    String extraClause;

    boolean browseStatus;
    boolean hide;

    @OneToMany(mappedBy = "car")
    Set<carImage> images;

    @ManyToOne
    @JoinColumn(name = "areaId")
    Area areas;

    @ManyToOne
    @JoinColumn(name = "typeId")
    carType type;
}
