package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.Like;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, String> {

    Optional<Report> findByIdAndIsDeletedIsFalse(String id);

    Page<Report> findAll(Pageable pageable);

    Page<Report> findAllByReportedBy(String reportedBy, Pageable pageable);

    Page<Report> findAllByReportEntityIdAndReportEntityType(String reportEntityId, FeedbackType reportEntityType, Pageable pageable);

//    Page<Report> findAllByReportEntityIdAndReportEntityTypeAndStatus(String reportEntityId, FeedbackType reportEntityType, Pageable pageable);

}
