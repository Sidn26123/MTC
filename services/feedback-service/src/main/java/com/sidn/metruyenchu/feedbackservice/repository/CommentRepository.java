package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.projection.GeneralCountProjectionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {
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

    @Query("SELECT c.parentId AS id, COUNT(c.id) AS count " +
            "FROM Comment c WHERE c.feedbackType = :feedbackType AND c.parentId IN :ids GROUP BY c.parentId")
    List<GeneralCountProjectionResponse> countChildComments(@Param("ids") List<String> ids,
                                                            @Param("feedbackType") FeedbackType feedbackType);




    Page<Comment> findAllByChapterId(String chapterId, Pageable pageable);

    Page<Comment> findAllByNovelId(String novelId, Pageable pageable);

    Page<Comment> findAllByCommentedBy(String userId, Pageable pageable);
}
