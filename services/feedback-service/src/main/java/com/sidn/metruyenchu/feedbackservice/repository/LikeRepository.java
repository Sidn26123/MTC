package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.Like;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, String> {
    Optional<Like> findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(String parentId, String likedBy, FeedbackType feedbackType);
}
