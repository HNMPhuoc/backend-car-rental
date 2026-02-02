package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.CarTypeResponse;
import com.phuoc.carRental.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarTypeRepository extends JpaRepository<CarType, UUID> {

    @Query("""
                select new com.phuoc.carRental.dto.responses.CarTypeResponse(
                    t.id, t.name, m.id, m.name
                )
                from CarType t
                join t.models m
            """)
    List<CarTypeResponse> findAllCarType();

    @Query("""
                select new com.phuoc.carRental.dto.responses.CarTypeResponse(
                    t.id, t.name, m.id, m.name
                )
                from CarType t
                join t.models m
                where t.id = :typeId
            """)
    Optional<CarTypeResponse> findCarTypeById(UUID typeId);

    boolean existsByNameIgnoreCaseAndModels_Id(String name, UUID modelId);

    boolean existsByNameIgnoreCaseAndModels_IdAndIdNot(
            String name,
            UUID modelId,
            UUID id
    );

}
