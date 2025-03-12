package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterStatusRepository extends JpaRepository<ChapterStatus, String> {
    boolean existsByName(String name);

    List<ChapterStatus> findAllById(ChapterStatus status);
}
