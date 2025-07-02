package com.sidn.metruyenchu.novelservice.spectification;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterFilterRequest;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.enums.ChapterState;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
/**
 * ChapterSpecification chứa các điều kiện lọc (Specification) cho entity Chapter.
 * Sử dụng kết hợp với Spring Data JPA để tạo truy vấn linh hoạt.
 */
public class ChapterSpecification {

    /**
     * Lọc theo ID của truyện (novel).
     */
    public static Specification<Chapter> hasNovelId(String novelId) {
        return (root, query, cb) -> {
            if (novelId == null) return null;
            return cb.equal(root.get("novel").get("id"), novelId);
        };
    }

    /**
     * Lọc theo tên chương (có thể chứa từ khóa, không phân biệt hoa thường).
     */
    public static Specification<Chapter> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    /**
     * Lọc theo tên người đăng chương.
     */
    public static Specification<Chapter> hasPublisher(String publisher) {
        return (root, query, cb) -> {
            if (publisher == null || publisher.isBlank()) return null;
            return cb.equal(root.get("publisher"), publisher);
        };
    }

    /**
     * Lọc theo chỉ số chương cụ thể.
     */
    public static Specification<Chapter> hasChapterIdx(Integer idx) {
        return (root, query, cb) -> {
            if (idx == null) return null;
            return cb.equal(root.get("chapterIdx"), idx);
        };
    }

    /**
     * Lọc theo khoảng chỉ số chương (chapterIdx).
     */
    public static Specification<Chapter> inChapterIdxRange(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("chapterIdx"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("chapterIdx"), min)
                    : cb.lessThanOrEqualTo(root.get("chapterIdx"), max);
        };
    }

    /**
     * Lọc theo khoảng lượt xem chương.
     */
    public static Specification<Chapter> inViewCountRange(Long min, Long max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("viewCount"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("viewCount"), min)
                    : cb.lessThanOrEqualTo(root.get("viewCount"), max);
        };
    }

    /**
     * Lọc theo khoảng số tiền cần để mở khóa chương.
     */
    public static Specification<Chapter> inAmountToUnlockRange(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("amountToUnlock"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("amountToUnlock"), min)
                    : cb.lessThanOrEqualTo(root.get("amountToUnlock"), max);
        };
    }

    /**
     * Lọc theo khoảng số bình luận của chương.
     */
    public static Specification<Chapter> inTotalCommentsRange(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("totalComments"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("totalComments"), min)
                    : cb.lessThanOrEqualTo(root.get("totalComments"), max);
        };
    }

    /**
     * Lọc theo trạng thái chương (CREATED, PUBLISHED,...).
     */
    public static Specification<Chapter> hasState(ChapterState state) {
        return (root, query, cb) -> {
            if (state == null) return null;
            return cb.equal(root.get("state"), state);
        };
    }

    /**
     * Lọc theo thời gian tạo chương (trong khoảng thời gian nhất định).
     */
    public static Specification<Chapter> createdBetween(LocalDateTime after, LocalDateTime before) {
        return (root, query, cb) -> {
            if (after == null && before == null) return null;
            if (after != null && before != null) return cb.between(root.get("createdAt"), after, before);
            return after != null
                    ? cb.greaterThanOrEqualTo(root.get("createdAt"), after)
                    : cb.lessThanOrEqualTo(root.get("createdAt"), before);
        };
    }

    /**
     * Lọc theo thời gian xuất bản chương (trong khoảng thời gian nhất định).
     */
    public static Specification<Chapter> publishedBetween(LocalDateTime after, LocalDateTime before) {
        return (root, query, cb) -> {
            if (after == null && before == null) return null;
            if (after != null && before != null) return cb.between(root.get("publishedAt"), after, before);
            return after != null
                    ? cb.greaterThanOrEqualTo(root.get("publishedAt"), after)
                    : cb.lessThanOrEqualTo(root.get("publishedAt"), before);
        };
    }



    /**
     * Lọc theo trạng thái hoạt động của chương.
     */
    public static Specification<Chapter> isActive(Boolean isActive) {
        return (root, query, cb) -> {
            if (isActive == null) return null;
            return cb.equal(root.get("isActive"), isActive);
        };
    }

    /**
     * Lọc theo trạng thái bị xóa mềm (soft delete).
     */
    public static Specification<Chapter> isDeleted(Boolean isDeleted) {
        return (root, query, cb) -> {
            if (isDeleted == null) return null;
            return cb.equal(root.get("isDeleted"), isDeleted);
        };
    }

    /**
     * Kết hợp toàn bộ các điều kiện lọc từ ChapterFilterRequest.
     */
    public static Specification<Chapter> filter(ChapterFilterRequest request) {
        return Specification
                .where(hasNovelId(request.getNovelId()))
                .and(hasName(request.getName()))
                .and(hasPublisher(request.getPublisher()))
                .and(hasChapterIdx(request.getChapterIdx()))
                .and(inChapterIdxRange(request.getMinChapterIdx(), request.getMaxChapterIdx()))
                .and(inViewCountRange(request.getMinViewCount(), request.getMaxViewCount()))
                .and(inAmountToUnlockRange(request.getMinAmountToUnlock(), request.getMaxAmountToUnlock()))
                .and(inTotalCommentsRange(request.getMinTotalComments(), request.getMaxTotalComments()))
                .and(hasState(request.getState()))
                .and(createdBetween(request.getCreatedAfter(), request.getCreatedBefore()))
                .and(publishedBetween(request.getPublishedAfter(), request.getPublishedBefore()))
                .and(isActive(request.getIsActive()))
                .and(isDeleted(request.getIsDeleted()));
    }
}
