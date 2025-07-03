package com.sidn.metruyenchu.identity_service.repository;

import com.sidn.metruyenchu.identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}