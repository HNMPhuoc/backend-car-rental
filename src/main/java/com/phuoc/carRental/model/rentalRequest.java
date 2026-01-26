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
public class rentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

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
    paymentMethod payMethod;

    @OneToOne(mappedBy = "rentRequest", cascade = CascadeType.ALL)
    rentalInvoice rentInvoice;

    @OneToOne(mappedBy = "rentRequest", cascade = CascadeType.ALL)
    rentalReceipt rentReceipt;
}
