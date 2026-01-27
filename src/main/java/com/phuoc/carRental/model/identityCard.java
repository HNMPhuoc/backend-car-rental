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
public class identityCard {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;

    @Column(nullable = false, unique = true)
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
