package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String>, JpaSpecificationExecutor<Chapter> {
    Optional<Chapter> findByNovel(Novel novel);
//    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterResponse(c) " +
//            "FROM Chapter c WHERE c.novel.id = :novelId AND c.isDeleted = false ORDER BY c.chapterIdx ASC")
//    List<ChapterResponse> findChaptersByNovelId(@Param("novelId") String novelId);
    Page<Chapter> findByNovelId(String novelId, Pageable pageable);
    Page<Chapter> findByNovel(Novel novel, Pageable pageable);
    Optional<Chapter> findByIdAndIsDeletedIsFalse(String id);
    Optional<Chapter> findByNovelSlugAndChapterIdxAndIsDeletedIsFalse(String slug, Integer chapterIdx);

    @Modifying
    @Query("UPDATE Chapter c SET c.totalComments = c.totalComments + 1 WHERE c.id = :chapterId")
    void incrementTotalComments(@Param("chapterId") String chapterId);

    @Modifying
    @Query("UPDATE Chapter c SET c.totalComments = c.totalComments - 1 WHERE c.id = :chapterId AND c.totalComments > 0")
    void decrementTotalComments(@Param("chapterId") String chapterId);

    //totalViews
    @Modifying
    @Query("UPDATE Chapter c SET c.totalViews = c.totalViews + 1 WHERE c.id = :chapterId")
    void incrementViewCount(@Param("chapterId") String chapterId);

    @Modifying
    @Query("UPDATE Chapter c SET c.totalViews = c.totalViews - 1 WHERE c.id = :chapterId AND c.totalViews > 0")
    void decrementViewCount(@Param("chapterId") String chapterId);


    @Modifying
    @Query("UPDATE Chapter c SET c.amountToUnlock = :amount WHERE c.id = :chapterId")
    void updateAmountToUnlock(@Param("chapterId") String chapterId, @Param("amount") Integer amount);

    @Modifying
    @Query("UPDATE Chapter c SET c.chapterIdx = c.chapterIdx + 1 WHERE c.novel = :novel AND c.chapterIdx >= :startIdx")
    void bulkUpdateChapterIdx(@Param("novel") Novel novel, @Param("startIdx") int startIdx);

    @Query("SELECT COUNT(c) FROM Chapter c WHERE c.novel.id = :novelId AND c.createdAt >= :startOfWeek")
    int countChaptersThisWeek(@Param("novelId") String novelId, @Param("startOfWeek") LocalDateTime startOfWeek);

    int countByNovelId(String novelId);

    Optional<Chapter> findByNovelAndChapterIdxAndIsDeletedIsFalse(Novel novel, int i);

    @Query(
            value = """
    SELECT c.*
    FROM chapter c
    JOIN (
        SELECT novel_id, MAX(published_at) as max_published
        FROM chapter
        WHERE is_deleted = false AND is_published = true AND is_public = true
        GROUP BY novel_id
    ) latest_chap ON c.novel_id = latest_chap.novel_id AND c.published_at = latest_chap.max_published
    ORDER BY c.published_at DESC
    LIMIT :limit
    """,
            nativeQuery = true
    )
    List<Chapter> findTopNLatestPublishedChaptersPerNovel(@Param("limit") int limit);


    @Query("SELECT c FROM Chapter c WHERE c.publishedAt <= :currentTime AND c.isPublished = false AND c.isDeleted = false")
    List<Chapter> findChaptersToPublish(@Param("currentTime") LocalDateTime currentTime);

    @Modifying
    @Query("UPDATE Chapter c SET c.isPublished = true, c.updatedAt = :updateTime WHERE c.id IN :chapterIds")
    int bulkUpdatePublishStatus(@Param("chapterIds") List<String> chapterIds, @Param("updateTime") LocalDateTime updateTime);
//    Optional<Chapter> findByNovelAndChapterIdxAndIsDeletedIsFalseAndPublishedAt
}
