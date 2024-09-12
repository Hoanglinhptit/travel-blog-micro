package com.travelblog.user_service.repository;

import com.travelblog.user_service.entites.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE " +
            "(:email IS NULL OR u.email LIKE %:email%) AND " +
            "(:name IS NULL OR u.name LIKE %:name%) AND " +
            "(:role IS NULL OR u.role LIKE %:role%)")
    Page<UserEntity> searchUsers(
            @Param("email") String email,
            @Param("name") String name,
            @Param("role") String role,
            Pageable pageable
    );

    Optional<UserEntity> findByEmail(String email);
}