package com.sidn.metruyenchu.user_service.repository;

import com.sidn.metruyenchu.user_service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<UserProfile> findByUserId(String userId);
}

