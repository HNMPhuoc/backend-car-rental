package com.phuoc.carRental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class carBrand {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;

    String name;

    @OneToMany(mappedBy = "brands")
    Set<carModel> carModels;
}
