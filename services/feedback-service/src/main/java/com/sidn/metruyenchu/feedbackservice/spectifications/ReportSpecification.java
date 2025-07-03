package com.sidn.metruyenchu.feedbackservice.spectifications;

import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.report.ReportFilterRequest;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.entity.Report;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.TargetType;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportStatus;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ReportSpecification {

    public static Specification<Report> hasReporterId(String reporterId) {
        return (root, query, cb) -> {
            if (reporterId == null || reporterId.isBlank()) return null;
            return cb.equal(root.get("reporterId"), reporterId);
        };
    }

    public static Specification<Report> hasReportType(ReportType reportType) {
        return (root, query, cb) -> {
            if (reportType == null) return null;
            return cb.equal(root.get("reportType"), reportType);
        };
    }

    public static Specification<Report> hasTargetType(TargetType targetType) {
        return (root, query, cb) -> {
            if (targetType == null) return null;
            return cb.equal(root.get("targetType"), targetType);
        };
    }

    public static Specification<Report> hasTargetId(String targetId) {
        return (root, query, cb) -> {
            if (targetId == null || targetId.isBlank()) return null;
            return cb.equal(root.get("targetId"), targetId);
        };
    }

    public static Specification<Report> hasTitleLike(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Report> hasPriority(Priority priority) {
        return (root, query, cb) -> {
            if (priority == null) return null;
            return cb.equal(root.get("priority"), priority);
        };
    }

    public static Specification<Report> hasStatus(ReportStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Report> hasAssignedTo(String assignedTo) {
        return (root, query, cb) -> {
            if (assignedTo == null || assignedTo.isBlank()) return null;
            return cb.equal(root.get("assignedTo"), assignedTo);
        };
    }

    public static Specification<Report> hasAssignedRole(AssigneeRole role) {
        return (root, query, cb) -> {
            if (role == null) return null;
            return cb.equal(root.get("assignedRole"), role);
        };
    }

    public static Specification<Report> isClosed(Boolean isClosed) {
        return (root, query, cb) -> {
            if (isClosed == null) return null;
            return cb.equal(root.get("isClosed"), isClosed);
        };
    }

    public static Specification<Report> isDeleted(Boolean isDeleted) {
        return (root, query, cb) -> {
            if (isDeleted == null) return null;
            return cb.equal(root.get("isDeleted"), isDeleted);
        };
    }

    public static Specification<Report> createdBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("createdAt"), from, to);
            return from != null
                    ? cb.greaterThanOrEqualTo(root.get("createdAt"), from)
                    : cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<Report> resolvedBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("resolvedAt"), from, to);
            return from != null
                    ? cb.greaterThanOrEqualTo(root.get("resolvedAt"), from)
                    : cb.lessThanOrEqualTo(root.get("resolvedAt"), to);
        };
    }

    public static Specification<Report> filter(ReportFilterRequest request) {
        return Specification
                .where(hasReporterId(request.getReporterId()))
                .and(hasReportType(request.getReportType()))
                .and(hasTargetType(request.getTargetType()))
                .and(hasTargetId(request.getTargetId()))
                .and(hasTitleLike(request.getTitle()))
                .and(hasPriority(request.getPriority()))
                .and(hasStatus(request.getStatus()))
                .and(hasAssignedTo(request.getAssignedTo()))
                .and(hasAssignedRole(request.getAssignedRole()))
                .and(isClosed(request.getIsClosed()))
                .and(isDeleted(request.getIsDeleted()))
                .and(createdBetween(request.getCreatedAfter(), request.getCreatedBefore()))
                .and(resolvedBetween(request.getResolvedAfter(), request.getResolvedBefore()));
    }
}
