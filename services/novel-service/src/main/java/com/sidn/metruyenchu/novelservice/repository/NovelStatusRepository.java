package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NovelStatusRepository extends JpaRepository<NovelStatus, String> {
    boolean existsByName(String name);
    Optional<NovelStatus> findByName(String name);
    List<NovelStatus> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);

    Optional<NovelStatus> findByIdIn(List<String> ids);
}
