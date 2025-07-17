package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.ChapterViewDistributionDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopChapterDto;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChapterStatisticsRepository extends JpaRepository<Chapter, String> {
    
    // Top chapters by bookmarks/views
//    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.request.statistic.TopChapterDto(c.id, c.name, c.novel.id, c.novel.name, " +
//           "0L, CAST(c.totalViews AS long), ROW_NUMBER() OVER (ORDER BY c.totalViews DESC)) " +
//           "FROM Chapter c WHERE c.isDeleted = false ORDER BY c.totalViews DESC")
//    List<TopChapterDto> getTopChaptersByViews(Pageable pageable);

    @Query(value = """
    SELECT c.id, c.name, c.novel_id, n.name AS novel_name, 
           0 AS total_bookmarks,
           c.total_views,
           ROW_NUMBER() OVER (ORDER BY c.total_views DESC) AS rank
    FROM chapter c
    JOIN novel n ON n.id = c.novel_id
    WHERE c.is_deleted = false
    ORDER BY c.total_views DESC
    """, nativeQuery = true)
    List<Object[]> getTopChaptersByViews(Pageable pageable);
    // Chapter view distribution
    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.request.statistic.ChapterViewDistributionDto(" +
           "CASE WHEN c.totalViews BETWEEN 0 AND 100 THEN '0-100' " +
           "     WHEN c.totalViews BETWEEN 101 AND 500 THEN '101-500' " +
           "     WHEN c.totalViews BETWEEN 501 AND 1000 THEN '501-1000' " +
           "     WHEN c.totalViews BETWEEN 1001 AND 5000 THEN '1001-5000' " +
           "     WHEN c.totalViews BETWEEN 5001 AND 10000 THEN '5001-10000' " +
           "     ELSE '10000+' END, " +
           "COUNT(c)) " +
           "FROM Chapter c WHERE c.isDeleted = false " +
           "GROUP BY CASE WHEN c.totalViews BETWEEN 0 AND 100 THEN '0-100' " +
           "              WHEN c.totalViews BETWEEN 101 AND 500 THEN '101-500' " +
           "              WHEN c.totalViews BETWEEN 501 AND 1000 THEN '501-1000' " +
           "              WHEN c.totalViews BETWEEN 1001 AND 5000 THEN '1001-5000' " +
           "              WHEN c.totalViews BETWEEN 5001 AND 10000 THEN '5001-10000' " +
           "              ELSE '10000+' END " +
           "ORDER BY MIN(c.totalViews)")
    List<ChapterViewDistributionDto> getChapterViewDistribution();
    
    // Total comments by time range
    @Query("SELECT COUNT(c) FROM Chapter c WHERE c.createdAt BETWEEN :startTime AND :endTime AND c.isDeleted = false")
    Long getTotalChapterCommentsBetween(@Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
}