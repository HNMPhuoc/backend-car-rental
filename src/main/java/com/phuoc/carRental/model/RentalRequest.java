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
public class RentalRequest {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;

    String carOwner;
    LocalDate reqDate;
    String pickupLoc;
    LocalDate pickupDate;
    LocalDate returnDate;
    double rentalDuration;
    double rentRate;
    double rentInsurance;
    double totalRentCost;
    double amountDue;
    boolean rentStatus;

    @OneToOne
    @JoinColumn(name = "payMethodId", nullable = false, unique = true)
    PaymentMethod payMethod;

    @OneToOne(mappedBy = "rentRequest", cascade = CascadeType.ALL)
    RentalInvoice rentInvoice;

    @OneToOne(mappedBy = "rentRequest", cascade = CascadeType.ALL)
    RentalReceipt rentReceipt;
}
