package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.ReportComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportCommentRepository extends JpaRepository<ReportComment, String> {
    List<ReportComment> findByReportIdOrderByCreatedAtAsc(String reportId);

    Page<ReportComment> findByReportId(String reportId, Pageable pageable);
}