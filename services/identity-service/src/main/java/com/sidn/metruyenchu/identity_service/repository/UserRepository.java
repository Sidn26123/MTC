package com.sidn.metruyenchu.identity_service.repository;

import com.sidn.metruyenchu.identity_service.entity.Role;
import com.sidn.metruyenchu.identity_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String googleId);

    @Override
    Page<User> findAll(Pageable pageable);

    List<User> findByRolesContaining(Role adminRole);
}