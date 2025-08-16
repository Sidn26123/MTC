package com.sidn.metruyenchu.feedbackservice.spectifications;

import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingFilterRequest;
import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RatingSpecification {

    public static Specification<Rating> hasNovelId(String novelId) {
        return (root, query, cb) -> {
            if (novelId == null || novelId.isBlank()) return null;
            return cb.equal(root.get("novelId"), novelId);
        };
    }

    public static Specification<Rating> hasRatedBy(String ratedBy) {
        return (root, query, cb) -> {
            if (ratedBy == null || ratedBy.isBlank()) return null;
            return cb.equal(root.get("ratedBy"), ratedBy);
        };
    }

    public static Specification<Rating> hasLastReadChapterId(String chapterId) {
        return (root, query, cb) -> {
            if (chapterId == null || chapterId.isBlank()) return null;
            return cb.equal(root.get("lastReadChapterId"), chapterId);
        };
    }

    public static Specification<Rating> hasLastReadChapterIdx(Integer idx) {
        return (root, query, cb) -> {
            if (idx == null) return null;
            return cb.equal(root.get("lastReadChapterIdx"), idx);
        };
    }

    // ---- FILTER CHO RATE ----
    public static Specification<Rating> hasRateBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("rate"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("rate"), min)
                    : cb.lessThanOrEqualTo(root.get("rate"), max);
        };
    }

    // ---- WORLD BUILDING ----
    public static Specification<Rating> hasWorldBuildingBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("worldBuildingRating"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("worldBuildingRating"), min)
                    : cb.lessThanOrEqualTo(root.get("worldBuildingRating"), max);
        };
    }

    // ---- CHARACTER DEVELOPMENT ----
    public static Specification<Rating> hasCharacterDevelopmentBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("characterDevelopmentRating"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("characterDevelopmentRating"), min)
                    : cb.lessThanOrEqualTo(root.get("characterDevelopmentRating"), max);
        };
    }

    // ---- NARRATIVE DEPTH ----
    public static Specification<Rating> hasNarrativeDepthBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("narrativeDepthRating"), min, max);
            return min != null
                    ? cb.greaterThanOrEqualTo(root.get("narrativeDepthRating"), min)
                    : cb.lessThanOrEqualTo(root.get("narrativeDepthRating"), max);
        };
    }

    public static Specification<Rating> isDeleted(Boolean isDeleted) {
        return (root, query, cb) -> {
            if (isDeleted == null) return null;
            return cb.equal(root.get("isDeleted"), isDeleted);
        };
    }

    public static Specification<Rating> isHidden(Boolean isHidden) {
        return (root, query, cb) -> {
            if (isHidden == null) return null;
            return cb.equal(root.get("isHidden"), isHidden);
        };
    }

    public static Specification<Rating> createdBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("createdAt"), from, to);
            return from != null
                    ? cb.greaterThanOrEqualTo(root.get("createdAt"), from)
                    : cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<Rating> updatedBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("updatedAt"), from, to);
            return from != null
                    ? cb.greaterThanOrEqualTo(root.get("updatedAt"), from)
                    : cb.lessThanOrEqualTo(root.get("updatedAt"), to);
        };
    }

    public static Specification<Rating> filter(RatingFilterRequest request) {
        return Specification
                .where(hasNovelId(request.getNovelId()))
                .and(hasRatedBy(request.getRatedBy()))
                .and(hasLastReadChapterId(request.getLastReadChapterId()))
                .and(hasLastReadChapterIdx(request.getLastReadChapterIdx()))
                .and(hasRateBetween(request.getRateMin(), request.getRateMax()))
                .and(hasWorldBuildingBetween(request.getWorldBuildingRatingMin(), request.getWorldBuildingRatingMax()))
                .and(hasCharacterDevelopmentBetween(request.getCharacterDevelopmentRatingMin(), request.getCharacterDevelopmentRatingMax()))
                .and(hasNarrativeDepthBetween(request.getNarrativeDepthRatingMin(), request.getNarrativeDepthRatingMax()))
                .and(isDeleted(request.getIsDeleted()))
                .and(isHidden(request.getIsHidden()))
                .and(createdBetween(request.getCreatedAfter(), request.getCreatedBefore()))
                .and(updatedBetween(request.getUpdatedAfter(), request.getUpdatedBefore()));
    }
}
