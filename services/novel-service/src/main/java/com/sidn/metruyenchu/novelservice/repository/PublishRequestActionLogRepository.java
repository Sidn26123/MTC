package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import com.sidn.metruyenchu.novelservice.entity.PublishRequestActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishRequestActionLogRepository extends JpaRepository<PublishRequestActionLog, String> {
}
