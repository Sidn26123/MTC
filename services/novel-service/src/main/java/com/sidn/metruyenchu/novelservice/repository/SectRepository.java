package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Sect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectRepository extends JpaRepository<Sect, String> {
    boolean existsByName(String name);
    Optional<Sect> findByName(String name);
    List<Sect> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);

    List<Sect> findByIdIn(List<String> sectIds);

    List<Sect> findAllByIsActiveAndIsDeletedOrderByName(boolean b, boolean b1);
}
