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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    UUID id;


    @Column(unique = true)
    String email;

    @Column(unique = true)
    String username;

    String password;

    @Column(unique = true)
    String phoneNum;

    String avatar;
    boolean online;
    boolean lock;

    @ManyToMany
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    IdentityCard card;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    DriverLicense driLicense;

    @OneToMany(mappedBy = "user")
    Set<Car> cars;

}
