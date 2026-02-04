package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.PaymentMethodResponse;
import com.phuoc.carRental.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    @Query("""
                select new com.phuoc.carRental.dto.responses.PaymentMethodResponse(
                    p.id, p.name
                )
                from PaymentMethod p
            """)
    List<PaymentMethodResponse> findAllPaymentMethod();

    @Query("""
                select new com.phuoc.carRental.dto.responses.PaymentMethodResponse(
                    p.id, p.name
                )
                from PaymentMethod p
                where p.id = :id
            """)
    Optional<PaymentMethodResponse> findPaymentMethodById(UUID id);
}
