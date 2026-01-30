package com.phuoc.carRental.repository;

import com.phuoc.carRental.dto.responses.PermissionResponse;
import com.phuoc.carRental.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    @Query("""
                select new com.phuoc.carRental.dto.responses.PermissionResponse(
                    p.id, p.name, p.description
                )
                from Permission p
            """)
    List<PermissionResponse> findAllPermission();
}
