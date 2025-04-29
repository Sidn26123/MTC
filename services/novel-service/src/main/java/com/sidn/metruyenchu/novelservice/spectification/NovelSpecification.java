package com.sidn.metruyenchu.novelservice.spectification;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelFilterRequest;
import com.sidn.metruyenchu.novelservice.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NovelSpecification {
    public static Specification<Novel> hasSearchText(String searchText) {
        return (root, query, cb) -> {
            if (searchText == null || searchText.trim().isEmpty()) return null;
            String pattern = "%" + searchText.trim() + "%";
            return cb.like(root.get("name"), pattern);
            // Nếu muốn lọc cả author: cb.or(cb.like(root.get("name"), ...), cb.like(root.join("author").get("name"), ...))
        };
    }

    public static Specification<Novel> hasSects(List<String> sectIds) {
        return (root, query, cb) -> {
            if (sectIds == null || sectIds.isEmpty()) return null;
            Join<Novel, NovelSect> join = root.join("novelSects", JoinType.INNER);
            return join.get("sect").get("id").in(sectIds);
        };
    }

    public static Specification<Novel> hasWorldScenes(List<String> worldSceneIds) {
        return (root, query, cb) -> {
            if (worldSceneIds == null || worldSceneIds.isEmpty()) return null;
            Join<Novel, NovelWorldScene> join = root.join("novelWorldScenes", JoinType.INNER);
            return join.get("worldScene").get("id").in(worldSceneIds);
        };
    }

    public static Specification<Novel> hasMainCharacterTraits(List<String> traitIds) {
        return (root, query, cb) -> {
            if (traitIds == null || traitIds.isEmpty()) return null;
            Join<Novel, NovelMainCharacterTrait> join = root.join("novelMainCharacterTraits", JoinType.INNER);
            return join.get("mainCharacterTrait").get("id").in(traitIds);
        };
    }

    public static Specification<Novel> hasGenres(List<String> genreIds) {
        return (root, query, cb) -> {
            if (genreIds == null || genreIds.isEmpty()) return null;
            Join<Novel, NovelGenre> join = root.join("novelGenres", JoinType.INNER);
            return join.get("genre").get("id").in(genreIds);
        };
    }

    public static Specification<Novel> hasStatus(List<String> statusIds) {
        return (root, query, cb) -> {
            if (statusIds == null || statusIds.isEmpty()) return null;
            Join<Novel, NovelStatusDetail> join = root.join("status", JoinType.INNER);
            return join.get("novelStatus").get("id").in(statusIds);
        };
    }

    public static Specification<Novel> isPublished(Boolean isPublished) {
        return (root, query, cb) -> {
            if (isPublished == null) return null;
            return cb.equal(root.get("isPublished"), isPublished);
        };
    }

    public static Specification<Novel> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("isDeleted"));
    }

    public static Specification<Novel> ofAuthor(String authorId) {
//        return (root, query, cb) -> {
//            if (authorId == null || authorId.trim().isEmpty()) return null;
//            return cb.equal(root.get("author").get("id"), authorId);
//        };
        return (root, query, cb) -> {
            if (authorId == null || authorId.trim().isEmpty()) return null;
            return cb.equal(root.get("author").get("id"), authorId);
        };
    }

    public static Specification<Novel> ofPublisher(String publisherId) {
        return (root, query, cb) -> {
            if (publisherId == null || publisherId.trim().isEmpty()) return null;
            return cb.equal(root.get("currentPublisher"), publisherId);
        };
    }

    public static Specification<Novel> filter(NovelFilterRequest request) {
        return Specification
                .where(NovelSpecification.hasSearchText(request.getSearchText()))
                .and(NovelSpecification.hasSects(request.getSects()))
                .and(NovelSpecification.hasWorldScenes(request.getWorldScenes()))
                .and(NovelSpecification.hasMainCharacterTraits(request.getMainCharacterTraits()))
                .and(NovelSpecification.hasGenres(request.getGenres()))
                .and(NovelSpecification.hasStatus(request.getStatus()))
                .and(NovelSpecification.isPublished(request.getIsPublished()))
                .and(NovelSpecification.ofAuthor(request.getAuthorId()))
                .and(NovelSpecification.ofPublisher(request.getCurrentPublisher()))
                .and(NovelSpecification.notDeleted())
                ;
    }

    public static Specification<Novel> fullyFilter(NovelFilterRequest request) {
        return Specification
                .where(NovelSpecification.hasSearchText(request.getSearchText()))
                .and(NovelSpecification.hasSects(request.getSects()))
                .and(NovelSpecification.hasWorldScenes(request.getWorldScenes()))
                .and(NovelSpecification.hasMainCharacterTraits(request.getMainCharacterTraits()))
                .and(NovelSpecification.hasGenres(request.getGenres()))
                .and(NovelSpecification.hasStatus(request.getStatus()))
                .and(NovelSpecification.isPublished(request.getIsPublished()))
                .and(NovelSpecification.ofAuthor(request.getAuthorId()))
                .and(NovelSpecification.ofPublisher(request.getCurrentPublisher()))
                ;
    }
//    public static Specification<Novel> filter(NovelFilterRequest request) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            // 📌 1. Lọc theo searchText (name hoặc author.name)
//            if (request.getSearchText() != null && !request.getSearchText().isEmpty()) {
//                String searchPattern = "%" + request.getSearchText() + "%";
//                predicates.add(cb.or(
//                        cb.like(root.get("name"), searchPattern)
////                        cb.like(root.join("author").get("name"), searchPattern)
//                ));
//            }
//
//            // 📌 2. Lọc theo sects (danh sách ID)
//            if (request.getSects() != null && !request.getSects().isEmpty()) {
//                Join<Novel, NovelSect> sectJoin = root.join("novelSects", JoinType.INNER);
//                predicates.add(sectJoin.get("sect").get("id").in(request.getSects()));
//            }
//
//            // 📌 3. Lọc theo worldScenes
//            if (request.getWorldScenes() != null && !request.getWorldScenes().isEmpty()) {
//                Join<Novel, NovelWorldScene> worldSceneJoin = root.join("novelWorldScenes", JoinType.INNER);
//                predicates.add(worldSceneJoin.get("worldScene").get("id").in(request.getWorldScenes()));
//            }
//
//            // 📌 4. Lọc theo mainCharacterTraits
//            if (request.getMainCharacterTraits() != null && !request.getMainCharacterTraits().isEmpty()) {
//                Join<Novel, NovelMainCharacterTrait> traitJoin = root.join("novelMainCharacterTraits", JoinType.INNER);
//                predicates.add(traitJoin.get("mainCharacterTrait").get("id").in(request.getMainCharacterTraits()));
//            }
//
//            // 📌 5. Lọc theo genres
//            if (request.getGenres() != null && !request.getGenres().isEmpty()) {
//                Join<Novel, NovelGenre> genreJoin = root.join("novelGenres", JoinType.INNER);
//                predicates.add(genreJoin.get("genre").get("id").in(request.getGenres()));
//            }
//
//            // 📌 6. Lọc theo status
//            if (request.getStatus() != null && !request.getStatus().isEmpty()) {
//                Join<Novel, NovelStatusDetail> statusJoin = root.join("status", JoinType.INNER);
//                predicates.add(statusJoin.get("novelStatus").get("id").in(request.getStatus()));
//            }
//
//            if (request.getIsPublished() != null) {
//                predicates.add(cb.equal(root.get("isPublished"), request.getIsPublished()));
//            }
//
//
//
//            // 📌 7. Lọc Novel chưa bị xóa
//            predicates.add(cb.isFalse(root.get("isDeleted")));
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }
}
