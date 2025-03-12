package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    @Modifying
    @Query("UPDATE Comment c SET c.totalLikes = c.totalLikes + 1 WHERE c.id = :commentId")
    void incrementTotalLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.totalLikes = c.totalLikes - 1 WHERE c.id = :commentId AND c.totalLikes > 0")
    void decrementTotalLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.totalDisLikes = c.totalDisLikes + 1 WHERE c.id = :commentId")
    void incrementTotalDisLikes(@Param("commentId") String commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.totalDisLikes = c.totalDisLikes - 1 WHERE c.id = :commentId AND c.totalDisLikes > 0")
    void decrementTotalDisLikes(@Param("commentId") String commentId);


    Page<Comment> findAllByCommentedInChapterId(String chapterId, Pageable pageable);
}
