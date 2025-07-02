package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.ReportAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportActionRepository extends JpaRepository<ReportAction, String> {
    List<ReportAction> findByReport_IdOrderByCreatedAtDesc(String reportId);
}
