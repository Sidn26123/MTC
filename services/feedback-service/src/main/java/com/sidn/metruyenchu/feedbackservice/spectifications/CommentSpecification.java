package com.sidn.metruyenchu.feedbackservice.spectifications;

import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentFilterRequest;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CommentSpecification {

    public static Specification<Comment> hasNovelId(String novelId) {
        return (root, query, cb) -> {
            if (novelId == null || novelId.isBlank()) return null;
            return cb.equal(root.get("novelId"), novelId);
        };
    }

    public static Specification<Comment> hasChapterId(String chapterId) {
        return (root, query, cb) -> {
            if (chapterId == null || chapterId.isBlank()) return null;
            return cb.equal(root.get("chapterId"), chapterId);
        };
    }

    public static Specification<Comment> hasParentId(String parentId) {
        return (root, query, cb) -> {
            if (parentId == null || parentId.isBlank()) return null;
            if (parentId.equals("null")) return cb.isNull(root.get("parentId")); //Neu = null thi lay cac comment goc
            return cb.equal(root.get("parentId"), parentId);
        };
    }

    public static Specification<Comment> hasCommentedBy(String userId) {
        return (root, query, cb) -> {
            if (userId == null || userId.isBlank()) return null;
            return cb.equal(root.get("commentedBy"), userId);
        };
    }

    public static Specification<Comment> hasFeedbackType(FeedbackType type) {
        return (root, query, cb) -> {
            if (type == null) return null;
            return cb.equal(root.get("feedbackType"), type);
        };
    }

    public static Specification<Comment> isDeleted(Boolean isDeleted) {
        return (root, query, cb) -> {
            if (isDeleted == null) return null;
            return cb.equal(root.get("isDeleted"), isDeleted);
        };
    }

    public static Specification<Comment> isHidden(Boolean isHidden) {
        return (root, query, cb) -> {
            if (isHidden == null) return null;
            return cb.equal(root.get("isHidden"), isHidden);
        };
    }

    public static Specification<Comment> createdBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("createdAt"), from, to);
            return from != null
                    ? cb.greaterThanOrEqualTo(root.get("createdAt"), from)
                    : cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<Comment> updatedBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("updatedAt"), from, to);
            return from != null
                    ? cb.greaterThanOrEqualTo(root.get("updatedAt"), from)
                    : cb.lessThanOrEqualTo(root.get("updatedAt"), to);
        };
    }

    public static Specification<Comment> filter(CommentFilterRequest request) {
        return Specification
                .where(hasNovelId(request.getNovelId()))
                .and(hasChapterId(request.getChapterId()))
                .and(hasParentId(request.getParentId()))
                .and(hasCommentedBy(request.getCommentedBy()))
                .and(hasFeedbackType(request.getFeedbackType()))
                .and(isDeleted(request.getIsDeleted()))
                .and(isHidden(request.getIsHidden()))
                .and(createdBetween(request.getCreatedAfter(), request.getCreatedBefore()))
                .and(updatedBetween(request.getUpdatedAfter(), request.getUpdatedBefore()));
    }
}
