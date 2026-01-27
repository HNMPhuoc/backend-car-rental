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
public class carModel {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;

    String name;

    @ManyToOne
    @JoinColumn(name = "brandId")
    carBrand brands;

    @OneToMany(mappedBy = "models")
    Set<carType> types;
}
