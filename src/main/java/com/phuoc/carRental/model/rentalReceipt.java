package com.phuoc.carRental.model;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class rentalReceipt {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;

    String renter;
    LocalDate invoiceDate;
    double deposit;
    double totalRental;
    double balanceDue;

    @OneToOne
    @JoinColumn(name = "reqId", nullable = false, unique = true)
    rentalRequest rentRequest;
}
