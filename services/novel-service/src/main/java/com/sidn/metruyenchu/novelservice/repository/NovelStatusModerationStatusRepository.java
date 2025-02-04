package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.NovelStatusModerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelStatusModerationStatusRepository extends JpaRepository<NovelStatusModerationStatus, String> {
}
