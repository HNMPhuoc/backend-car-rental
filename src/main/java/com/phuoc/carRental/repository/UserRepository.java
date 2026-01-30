package com.phuoc.carRental.repository;

import com.phuoc.carRental.model.Role;
import com.phuoc.carRental.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
                select distinct u
                from User u
                join u.roles r
                where (:role is null or r = :role)
                  and (:online is null or u.online = :online)
                  and (:lock is null or u.lock = :lock)
            """)
    Page<User> findByRoleAndOnlineAndLockOptional(
            @Param("role") Role role,
            @Param("online") Boolean online,
            @Param("lock") Boolean lock,
            Pageable pageable
    );

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNum(String phoneNum);

    boolean existsByEmailOrUsernameOrPhoneNum(
            String email,
            String username,
            String phoneNum
    );


}
