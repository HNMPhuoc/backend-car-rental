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
public class rentalInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String carOwner;
    String licensePlate;
    String phoneNum;
    LocalDate invoiceDate;
    double totalReceive;

    @OneToOne
    @JoinColumn(name = "reqId", nullable = false, unique = true)
    rentalRequest rentRequest;
}
