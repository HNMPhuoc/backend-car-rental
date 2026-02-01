package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.CarBrandResponse;
import com.phuoc.carRental.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    @Query("""
                select new com.phuoc.carRental.dto.responses.CarBrandResponse(
                    b.id, b.name
                )
                from CarBrand b
            """)
    List<CarBrandResponse> findAllCarBrand();

    @Query("""
                select new com.phuoc.carRental.dto.responses.CarBrandResponse(
                    b.id, b.name
                )
                from CarBrand b
                where b.id = :brandId
            """)
    Optional<CarBrandResponse> findCarBrandById(UUID brandId);

    boolean existsByNameIgnoreCase(String name);
}
