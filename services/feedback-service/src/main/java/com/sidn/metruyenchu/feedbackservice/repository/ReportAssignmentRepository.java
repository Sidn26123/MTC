package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.ReportAssignment;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportAssignmentRepository extends JpaRepository<ReportAssignment, String> {
    Optional<ReportAssignment> findByReportIdAndAssigneeId(String reportId, String assigneeId);
    List<ReportAssignment> findByReportId(String reportId);

    @Query("""
        SELECT ra.assigneeId, COUNT(ra)
        FROM ReportAssignment ra
        WHERE ra.report.status IN (:activeStatuses)
        AND ra.assigneeId IN (:staffIds)
        GROUP BY ra.assigneeId
        ORDER BY COUNT(ra) ASC
    """)
    List<Object[]> countActiveReportsByAssignee(@Param("staffIds") List<String> staffIds,
                                                @Param("activeStatuses") List<ReportStatus> activeStatuses);
}