package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.CarModelResponse;
import com.phuoc.carRental.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, UUID> {

    @Query("""
                select new com.phuoc.carRental.dto.responses.CarModelResponse(
                    m.id, m.name, m.brands.id, m.brands.name
                )
                from CarModel m
            """)
    List<CarModelResponse> findAllCarModel();

    @Query("""
                select new com.phuoc.carRental.dto.responses.CarModelResponse(
                    m.id, m.name, m.brands.id, m.brands.name
                )
                from CarModel m
                where m.id = :modelId
            """)
    Optional<CarModelResponse> findCarModelById(UUID modelId);

    boolean existsByNameIgnoreCaseAndBrands_Id(String name, UUID brandId);
}
