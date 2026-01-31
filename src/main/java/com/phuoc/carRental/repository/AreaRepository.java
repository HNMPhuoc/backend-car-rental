package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.AreaResponse;
import com.phuoc.carRental.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID> {
    @Query("""
                select new com.phuoc.carRental.dto.responses.AreaResponse(a.id, a.name)
                from Area a
            """)
    List<AreaResponse> findAllArea();
}
