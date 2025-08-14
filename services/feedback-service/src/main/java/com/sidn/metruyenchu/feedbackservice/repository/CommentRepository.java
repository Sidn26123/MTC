package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.projection.GeneralCountProjectionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.shared_library.enums.feedback.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    Page<Comment> findAllByNovelIdAndIsDeletedIsFalse(String novelId, Pageable pageable);

    // =============================
    // Thống kê comment theo MONTH (input = year)
    // =============================

    @Query("""
    SELECT MONTH(c.createdAt) AS month, COUNT(c.id) AS totalComments
    FROM Comment c
    WHERE YEAR(c.createdAt) = :year
      AND c.isDeleted = false
      AND (:isAll = true OR c.novelId IN :novelIds)
    GROUP BY MONTH(c.createdAt)
    ORDER BY MONTH(c.createdAt)
""")
    List<Object[]> countMonthly(@Param("novelIds") List<String> novelIds,
                                @Param("year") int year,
                                @Param("isAll") boolean isAll);

    @Query("""
    SELECT DAY(c.createdAt) AS day, COUNT(c.id) AS totalComments
    FROM Comment c
    WHERE YEAR(c.createdAt) = :year
      AND MONTH(c.createdAt) = :month
      AND c.isDeleted = false
      AND (:isAll = true OR c.novelId IN :novelIds)
    GROUP BY DAY(c.createdAt)
    ORDER BY DAY(c.createdAt)
""")
    List<Object[]> countDaily(@Param("novelIds") List<String> novelIds,
                              @Param("year") int year,
                              @Param("month") int month,
                              @Param("isAll") boolean isAll);

    @Query("""
        SELECT COUNT(c) 
        FROM Comment c 
        WHERE c.novelId IN :novelIds 
          AND c.isDeleted = false 
          AND c.isHidden = false
          AND c.createdAt BETWEEN :from AND :to
    """)
    long countTotalComments(@Param("novelIds") List<String> novelIds,
                            @Param("from") LocalDateTime from,
                            @Param("to") LocalDateTime to);

    @Query("""
        SELECT c.novelId, COUNT(c) 
        FROM Comment c 
        WHERE c.createdAt BETWEEN :from AND :to
          AND c.isDeleted = false
          AND c.isHidden = false
          AND  (:isAll = true OR c.novelId IN :novelIds)
        GROUP BY c.novelId
    """)
    List<Object[]> countCommentsByNovel(@Param("novelIds") List<String> novelIds,
                                        @Param("isAll") boolean isAll,
                                        @Param("from") LocalDateTime from,
                                        @Param("to") LocalDateTime to);

    @Query("""
    SELECT FUNCTION('DATE', c.createdAt), COUNT(c) 
    FROM Comment c
    WHERE (:isAll = true OR c.novelId IN :novelIds)
      AND c.isDeleted = false
      AND c.isHidden = false
      AND c.createdAt BETWEEN :from AND :to
    GROUP BY FUNCTION('DATE', c.createdAt)
    ORDER BY FUNCTION('DATE', c.createdAt)
""")
    List<Object[]> countCommentsOverTime(@Param("novelIds") List<String> novelIds,
                                         @Param("isAll") boolean isAll,
                                         @Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);

    @Query("""
    SELECT c.chapterId, COUNT(c)
    FROM Comment c
    WHERE (:isAll = true OR c.novelId IN :novelIds)
      AND c.isDeleted = false
      AND c.isHidden = false
      AND c.createdAt BETWEEN :from AND :to
    GROUP BY c.chapterId
    ORDER BY COUNT(c) DESC
""")
    List<Object[]> topChaptersByComments(@Param("novelIds") List<String> novelIds,
                                         @Param("isAll") boolean isAll,
                                         @Param("from") LocalDateTime from,
                                         @Param("to") LocalDateTime to);

//    @Query("""
//        SELECT FUNCTION('DAY_OF_WEEK', c.createdAt), FUNCTION('HOUR', c.createdAt), COUNT(c)
//        FROM Comment c
//        WHERE c.novelId IN :novelIds
//          AND c.isDeleted = false
//          AND c.isHidden = false
//          AND c.createdAt BETWEEN :from AND :to
//        GROUP BY FUNCTION('DAY_OF_WEEK', c.createdAt), FUNCTION('HOUR', c.createdAt)
//    """)
//    List<Object[]> countCommentsHeatmap(@Param("novelIds") List<String> novelIds,
//                                        @Param("from") LocalDateTime from,
//                                        @Param("to") LocalDateTime to);
//
    @Query(value = """
        SELECT 
            EXTRACT(DOW FROM c.created_at) AS dayOfWeek,
            EXTRACT(HOUR FROM c.created_at) AS hourOfDay,
            COUNT(c.id) AS commentCount
        FROM comment c
        WHERE c.novel_id IN (:novelIds)
          AND c.is_deleted = false
          AND c.is_hidden = false
          AND c.created_at BETWEEN :fromDate AND :toDate
        GROUP BY EXTRACT(DOW FROM c.created_at), EXTRACT(HOUR FROM c.created_at)
        """, nativeQuery = true)
    List<Object[]> countCommentsHeatmap(
            @Param("novelIds") List<String> novelIds,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query(value = """
        SELECT c.content
        FROM comment c
        WHERE c.novel_id IN (:novelIds)
          AND c.is_deleted = false
          AND c.is_hidden = false
          AND c.created_at BETWEEN :fromDate AND :toDate
    """, nativeQuery = true)
    List<String> findContentsByNovelIdsAndDateRange(
            @Param("novelIds") List<String> novelIds,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    /**
     * Đếm số lượng comment theo novelId (cho biểu đồ tròn)
     */
    @Query(value = "SELECT novel_id, COUNT(*) as comment_count " +
            "FROM comment c " +
            "WHERE c.novel_id IN :novelIds AND c.is_deleted = false " +
            "GROUP BY c.novel_id " +
            "ORDER BY comment_count DESC",
            nativeQuery = true)
    List<Object[]> countCommentsByNovelIds(@Param("novelIds") List<String> novelIds);

    /**
     * Đếm comment theo thời gian - nhóm theo ngày (cho multiple novels)
     * Sử dụng native query để tránh lỗi orderable attribute
     */
    @Query(value = "SELECT DATE(created_at) as comment_date, COUNT(*) as comment_count " +
            "FROM comment c " +
            "WHERE c.novel_id IN :novelIds AND c.is_deleted = false " +
            "AND c.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(c.created_at) " +
            "ORDER BY comment_date",
            nativeQuery = true)
    List<Object[]> countCommentsByDateRange(@Param("novelIds") List<String> novelIds,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    /**
     * Đếm comment theo thời gian - nhóm theo tuần
     */
    @Query(value = """
    SELECT 
        EXTRACT(ISOYEAR FROM c.created_at) AS year,
        EXTRACT(WEEK FROM c.created_at) AS week,
        COUNT(*) AS comment_count
    FROM comment c
    WHERE c.novel_id IN (:novelIds)
      AND c.is_deleted = false
      AND c.created_at BETWEEN :startDate AND :endDate
    GROUP BY year, week
    ORDER BY year, week
    """, nativeQuery = true)
    List<Object[]> countCommentsByWeek(
            @Param("novelIds") List<String> novelIds,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Đếm comment theo thời gian - nhóm theo tháng
     */
    @Query(value = "SELECT EXTRACT(YEAR FROM created_at) AS year, " +
            "EXTRACT(MONTH FROM created_at) AS month, " +
            "COUNT(*) AS comment_count " +
            "FROM comment c " +
            "WHERE c.novel_id IN :novelIds AND c.is_deleted = false " +
            "AND c.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY EXTRACT(YEAR FROM created_at), EXTRACT(MONTH FROM created_at) " +
            "ORDER BY year, month",
            nativeQuery = true)
    List<Object[]> countCommentsByMonth(@Param("novelIds") List<String> novelIds,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);


    /**
     * Đếm comment theo giờ trong ngày (cho 1 truyện)
     */
    @Query(value = "SELECT EXTRACT(HOUR FROM created_at) AS hour, COUNT(*) AS comment_count " +
            "FROM comment c " +
            "WHERE c.novel_id = :novelId AND c.is_deleted = false " +
            "AND DATE(c.created_at) = :date " +
            "GROUP BY EXTRACT(HOUR FROM created_at) " +
            "ORDER BY hour",
            nativeQuery = true)
    List<Object[]> countCommentsByHourInDay(@Param("novelId") String novelId,
                                            @Param("date") LocalDate date);


    /**
     * Đếm comment theo ngày (cho 1 truyện)
     */
    @Query(value = "SELECT CAST(created_at AS DATE) AS comment_date, COUNT(*) AS comment_count " +
            "FROM comment c " +
            "WHERE c.novel_id = :novelId AND c.is_deleted = false " +
            "AND c.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(created_at AS DATE) " +
            "ORDER BY comment_date",
            nativeQuery = true)
    List<Object[]> countCommentsByDateForSingleNovel(@Param("novelId") String novelId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);


}


