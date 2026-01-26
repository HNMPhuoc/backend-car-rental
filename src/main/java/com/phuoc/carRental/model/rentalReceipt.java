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
public class rentalReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String renter;
    LocalDate invoiceDate;
    double deposit;
    double totalRental;
    double balanceDue;

    @OneToOne
    @JoinColumn(name = "reqId", nullable = false, unique = true)
    rentalRequest rentRequest;
}
