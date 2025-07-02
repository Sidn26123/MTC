package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//public interface ReportRepository extends JpaRepository<Report, String> {
//
//    Optional<Report> findByIdAndIsDeletedIsFalse(String id);
//
//    Page<Report> findAll(Pageable pageable);
//
//    Page<Report> findAllByReportedBy(String reportedBy, Pageable pageable);
//
//    Page<Report> findAllByReportEntityIdAndReportType(String reportEntityId, ReportType reportType, Pageable pageable);
//
//    List<Report> findByStatusAndCreatedAtBefore(ReportStatus reportStatus, LocalDateTime cutoff);
//
//    Page<Report> findByAssignedToAndAssignedRole(String userId, UserRole userRole, Pageable pageable);
//
//    Page<Report> findByReporterId(String userId, Pageable pageable);
//
////    Page<Report> findAllByReportEntityIdAndReportEntityTypeAndStatus(String reportEntityId, FeedbackType reportEntityType, Pageable pageable);
//
//}
public interface ReportRepository extends JpaRepository<Report, String> {

    // Tìm report chưa bị xóa
    Optional<Report> findByIdAndIsDeletedIsFalse(String id);

    // Lấy tất cả report
    Page<Report> findAll(Pageable pageable);

    // Lấy tất cả report do user gửi (dùng đúng tên field trong entity: reporterId)
    Page<Report> findAllByReporterId(String reporterId, Pageable pageable);

    // Lọc theo reportEntityId và reportType
    Page<Report> findAllByReportEntityIdAndReportType(String reportEntityId, ReportType reportType, Pageable pageable);

    // Tìm các report có trạng thái cụ thể và được tạo trước thời điểm nào đó (để auto xóa, auto đóng,...)
    List<Report> findByStatusAndCreatedAtBefore(ReportStatus reportStatus, LocalDateTime cutoff);

    // Lấy các report được giao cho một user cụ thể
    Page<Report> findByAssignedToAndAssignedRole(String assignedTo, UserRole assignedRole, Pageable pageable);

    // Alias (có thể trùng với cái trên), lấy các report do user tạo
    Page<Report> findByReporterId(String reporterId, Pageable pageable);
}
// Nếu bạn cần filter thêm theo entityType (FeedbackType), có thể dùng meth
