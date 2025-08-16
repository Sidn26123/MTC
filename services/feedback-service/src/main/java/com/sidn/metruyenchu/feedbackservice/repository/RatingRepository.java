package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.dto.response.projection.GeneralCountProjectionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String>, JpaSpecificationExecutor<Rating> {
    @Modifying
    @Query("UPDATE Rating c SET c.totalLikes = c.totalLikes + 1 WHERE c.id = :commentId")
    void incrementTotalLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Rating c SET c.totalLikes = c.totalLikes - 1 WHERE c.id = :commentId AND c.totalLikes > 0")
    void decrementTotalLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Rating c SET c.totalDislikes = c.totalDislikes + 1 WHERE c.id = :commentId")
    void incrementTotalDisLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Rating c SET c.totalDislikes = c.totalDislikes - 1 WHERE c.id = :commentId AND c.totalDislikes > 0")
    void decrementTotalDisLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Rating c SET c.isDeleted = true WHERE c.id = :commentId")
    void softDelete(@Param("commentId") String commentId);



    Page<Rating> findALlByNovelId(String ratingInNovelId,
                                  Pageable pageable);

    @Query("SELECT FLOOR(r.rate) as star, COUNT(r) as total " +
            "FROM Rating r " +
            "WHERE r.novelId = :novelId AND r.isDeleted = false " +
            "GROUP BY FLOOR(r.rate)")
    List<Object[]> countRatingsGroupedByStar(@Param("novelId") String novelId);

    Page<Rating> findAllByIsDeletedFalseAndIsHiddenFalseOrderByCreatedAtDesc(Pageable pageable);
}
