package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.IdentityCardResponse;
import com.phuoc.carRental.model.IdentityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IdentityCardRepository extends JpaRepository<IdentityCard, UUID> {

    boolean existsByCccd(String cccd);

    boolean existsByCccdAndIdNot(String cccd, UUID id);

    boolean existsByUser_Id(UUID userId);

    @Query("""
                select new com.phuoc.carRental.dto.responses.IdentityCardResponse(
                    c.id, c.cccd, c.fullName, c.gender, c.dob,
                    c.birthPlace, c.resAddr, c.nationally, c.urlImage,
                    u.id
                )
                from IdentityCard c
                join c.user u
            """)
    List<IdentityCardResponse> findAllIdentityCard();

    @Query("""
                select new com.phuoc.carRental.dto.responses.IdentityCardResponse(
                    c.id, c.cccd, c.fullName, c.gender, c.dob,
                    c.birthPlace, c.resAddr, c.nationally, c.urlImage,
                    u.id
                )
                from IdentityCard c
                join c.user u
                where c.id = :id
            """)
    Optional<IdentityCardResponse> findIdentityCardById(UUID id);
}
