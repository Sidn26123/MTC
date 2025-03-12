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

    public static Specification<Novel> filter(NovelFilterRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 📌 1. Lọc theo searchText (name hoặc author.name)
            if (request.getSearchText() != null && !request.getSearchText().isEmpty()) {
                String searchPattern = "%" + request.getSearchText() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("name"), searchPattern)
//                        cb.like(root.join("author").get("name"), searchPattern)
                ));
            }

            // 📌 2. Lọc theo sects (danh sách ID)
            if (request.getSects() != null && !request.getSects().isEmpty()) {
                Join<Novel, NovelSect> sectJoin = root.join("novelSects", JoinType.INNER);
                predicates.add(sectJoin.get("sect").get("id").in(request.getSects()));
            }

            // 📌 3. Lọc theo worldScenes
            if (request.getWorldScenes() != null && !request.getWorldScenes().isEmpty()) {
                Join<Novel, NovelWorldScene> worldSceneJoin = root.join("novelWorldScenes", JoinType.INNER);
                predicates.add(worldSceneJoin.get("worldScene").get("id").in(request.getWorldScenes()));
            }

            // 📌 4. Lọc theo mainCharacterTraits
            if (request.getMainCharacterTraits() != null && !request.getMainCharacterTraits().isEmpty()) {
                Join<Novel, NovelMainCharacterTrait> traitJoin = root.join("novelMainCharacterTraits", JoinType.INNER);
                predicates.add(traitJoin.get("mainCharacterTrait").get("id").in(request.getMainCharacterTraits()));
            }

            // 📌 5. Lọc theo genres
            if (request.getGenres() != null && !request.getGenres().isEmpty()) {
                Join<Novel, NovelGenre> genreJoin = root.join("novelGenres", JoinType.INNER);
                predicates.add(genreJoin.get("genre").get("id").in(request.getGenres()));
            }

            // 📌 6. Lọc theo status
            if (request.getStatus() != null && !request.getStatus().isEmpty()) {
                Join<Novel, NovelStatusDetail> statusJoin = root.join("status", JoinType.INNER);
                predicates.add(statusJoin.get("novelStatus").get("id").in(request.getStatus()));
            }

            // 📌 7. Lọc Novel chưa bị xóa
            predicates.add(cb.isFalse(root.get("isDeleted")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
