package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.CarListResponse;
import com.phuoc.carRental.dto.responses.CarUserListResponse;
import com.phuoc.carRental.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    // Admin: List cars with filter by hide and browseStatus
    @Query("""
                select new com.phuoc.carRental.dto.responses.CarListResponse(
                    c.id, c.licensePlate, c.manuYear, c.fuelType, c.thumbnail
                )
                from Car c
                where (:hide is null or c.hide = :hide)
                  and (:browse is null or c.browseStatus = :browse)
                  and (:areaId is null or c.areas.id = :areaId)
                  and (:typeId is null or c.type.id = :typeId)
            """)
    Page<CarListResponse> filterCars(
            @Param("hide") Boolean hide,
            @Param("browse") Boolean browse,
            @Param("areaId") UUID areaId,
            @Param("typeId") UUID typeId,
            Pageable pageable
    );

    // User: List cars with hide=false and browseStatus=true
    @Query("""
                select new com.phuoc.carRental.dto.responses.CarListResponse(
                    c.id, c.licensePlate, c.manuYear, c.fuelType, c.thumbnail
                )
                from Car c
                where c.hide = false
                  and c.browseStatus = true
                  and (:areaId is null or c.areas.id = :areaId)
                  and (:typeId is null or c.type.id = :typeId)
            """)
    Page<CarListResponse> getUserBrowsableCars(
            @Param("areaId") UUID areaId,
            @Param("typeId") UUID typeId,
            Pageable pageable
    );

    // User: List user's own cars
    @Query("""
                select new com.phuoc.carRental.dto.responses.CarUserListResponse(
                    c.id, c.licensePlate, c.manuYear, c.fuelType, c.thumbnail,
                    c.maintStatus, c.browseStatus, c.hide
                )
                from Car c
                where c.user.id = :userId
            """)
    Page<CarUserListResponse> getUserCars(
            @Param("userId") UUID userId,
            Pageable pageable
    );

    // Employee: Approve car (set browseStatus = true)
    @Modifying
    @Query("update Car c set c.browseStatus = true where c.id = :id")
    void approveCar(UUID id);

    // User: Update hide status
    @Modifying
    @Query("update Car c set c.hide = :hide where c.id = :id")
    void updateHideStatus(@Param("id") UUID id, @Param("hide") boolean hide);

    // User: Update maintenance status
    @Modifying
    @Query("update Car c set c.maintStatus = :maintStatus where c.id = :id")
    void updateMaintStatus(@Param("id") UUID id, @Param("maintStatus") boolean maintStatus);

    // Check if car exists and belongs to user
    @Query("select case when count(c) > 0 then true else false end from Car c where c.id = :id and c.user.id = :userId")
    boolean existsByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);
}
