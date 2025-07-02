package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.dto.response.projection.GeneralCountProjectionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Like;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, String> {
    Optional<Like> findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(String parentId, String likedBy, FeedbackType feedbackType);
    Optional<Like> findLikeByParentIdAndLikedByAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(String parentId, String userId, String likedBy, FeedbackType feedbackType);

    Page<Like> findAllByIsDeletedIsFalse(Pageable pageable);

    @Query("SELECT c.parentId AS id, COUNT(c.parentId) AS count " +
            "FROM Like c WHERE c.feedbackType = :feedbackType AND c.parentId IN :ids AND c.isDeleted = False AND c.isLiked = :isLiked GROUP BY c.parentId")
    List<GeneralCountProjectionResponse> countLike(@Param("ids") List<String> ids,
                                                   @Param("feedbackType") FeedbackType feedbackType,
                                                   @Param("isLiked") boolean isLiked);
}
