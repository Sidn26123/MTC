package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.ReportAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportAssignmentRepository extends JpaRepository<ReportAssignment, String> {
    Optional<ReportAssignment> findByReportIdAndAssigneeId(String reportId, String assigneeId);
    List<ReportAssignment> findByReportId(String reportId);
}