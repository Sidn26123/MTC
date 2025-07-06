package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelCompletionDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopNovelDto;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.shared_library.dto.request.TimeRangeStatisticDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NovelStatisticsRepository extends JpaRepository<Novel, String> {
    
    // Novel Creation Statistics
    @Query("SELECT COUNT(n) FROM Novel n WHERE n.createdAt BETWEEN :startTime AND :endTime AND n.isDeleted = false")
    Long countNovelsCreatedBetween(@Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);
    
//    @Query("SELECT new com.sidn.metruyenchu.shared_library.dto.request.TimeRangeStatisticDto(" +
//           "CASE WHEN :segments = 7 THEN DATE_TRUNC('day', n.createdAt) " +
//           "     WHEN :segments = 52 THEN DATE_TRUNC('week', n.createdAt) " +
//           "     WHEN :segments = 12 THEN DATE_TRUNC('month', n.createdAt) " +
//           "     ELSE DATE_TRUNC('day', n.createdAt) END, " +
//           "CASE WHEN :segments = 7 THEN DATE_TRUNC('day', n.createdAt) + INTERVAL '1 day' - INTERVAL '1 second' " +
//           "     WHEN :segments = 52 THEN DATE_TRUNC('week', n.createdAt) + INTERVAL '1 week' - INTERVAL '1 second' " +
//           "     WHEN :segments = 12 THEN DATE_TRUNC('month', n.createdAt) + INTERVAL '1 month' - INTERVAL '1 second' " +
//           "     ELSE DATE_TRUNC('day', n.createdAt) + INTERVAL '1 day' - INTERVAL '1 second' END, " +
//           "COUNT(n)) " +
//           "FROM Novel n " +
//           "WHERE n.createdAt BETWEEN :startTime AND :endTime AND n.isDeleted = false " +
//           "GROUP BY DATE_TRUNC(CASE WHEN :segments = 7 THEN 'day' " +
//           "                         WHEN :segments = 52 THEN 'week' " +
//           "                         WHEN :segments = 12 THEN 'month' " +
//           "                         ELSE 'day' END, n.createdAt) " +
//           "ORDER BY DATE_TRUNC(CASE WHEN :segments = 7 THEN 'day' " +
//           "                         WHEN :segments = 52 THEN 'week' " +
//           "                         WHEN :segments = 12 THEN 'month' " +
//           "                         ELSE 'day' END, n.createdAt)")
//    List<TimeRangeStatisticDto> getNovelsCreatedByTimeSegments(@Param("startTime") LocalDateTime startTime,
//                                                               @Param("endTime") LocalDateTime endTime,
//                                                               @Param("segments") Integer segments);
    @Query(value = """
    SELECT DATE_TRUNC('day', n.created_at)    AS start_time,
           DATE_TRUNC('day', n.created_at) + INTERVAL '1 day' - INTERVAL '1 second' AS end_time,
           COUNT(*) AS total
    FROM novel n
    WHERE n.created_at BETWEEN :start AND :end AND n.is_deleted = false
    GROUP BY start_time
    ORDER BY start_time
    """, nativeQuery = true)
    List<Object[]> getCountByDay(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);

    @Query(value = """
    SELECT DATE_TRUNC('week', n.created_at)    AS start_time,
           DATE_TRUNC('week', n.created_at) + INTERVAL '1 week' - INTERVAL '1 second' AS end_time,
           COUNT(*) AS total
    FROM novel n
    WHERE n.created_at BETWEEN :start AND :end AND n.is_deleted = false
    GROUP BY start_time
    ORDER BY start_time
    """, nativeQuery = true)
    List<Object[]> getCountByWeek(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end);

    @Query(value = """
    SELECT DATE_TRUNC('month', n.created_at)    AS start_time,
           DATE_TRUNC('month', n.created_at) + INTERVAL '1 month' - INTERVAL '1 second' AS end_time,
           COUNT(*) AS total
    FROM novel n
    WHERE n.created_at BETWEEN :start AND :end AND n.is_deleted = false
    GROUP BY start_time
    ORDER BY start_time
    """, nativeQuery = true)
    List<Object[]> getCountByMonth(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

    // Novel Approval Statistics
    @Query("SELECT COUNT(n) FROM Novel n WHERE n.novelState = 'APPROVED' AND n.updatedAt BETWEEN :startTime AND :endTime AND n.isDeleted = false")
    Long countNovelsApprovedBetween(@Param("startTime") LocalDateTime startTime, 
                                    @Param("endTime") LocalDateTime endTime);
    
//    @Query("SELECT new com.example.dto.TimeRangeStatisticDto(" +
//           "CASE WHEN :segments = 7 THEN DATE_TRUNC('day', n.updatedAt) " +
//           "     WHEN :segments = 52 THEN DATE_TRUNC('week', n.updatedAt) " +
//           "     WHEN :segments = 12 THEN DATE_TRUNC('month', n.updatedAt) " +
//           "     ELSE DATE_TRUNC('day', n.updatedAt) END, " +
//           "CASE WHEN :segments = 7 THEN DATE_TRUNC('day', n.updatedAt) + INTERVAL '1 day' - INTERVAL '1 second' " +
//           "     WHEN :segments = 52 THEN DATE_TRUNC('week', n.updatedAt) + INTERVAL '1 week' - INTERVAL '1 second' " +
//           "     WHEN :segments = 12 THEN DATE_TRUNC('month', n.updatedAt) + INTERVAL '1 month' - INTERVAL '1 second' " +
//           "     ELSE DATE_TRUNC('day', n.updatedAt) + INTERVAL '1 day' - INTERVAL '1 second' END, " +
//           "COUNT(n)) " +
//           "FROM Novel n " +
//           "WHERE n.novelState = 'APPROVED' AND n.updatedAt BETWEEN :startTime AND :endTime AND n.isDeleted = false " +
//           "GROUP BY DATE_TRUNC(CASE WHEN :segments = 7 THEN 'day' " +
//           "                         WHEN :segments = 52 THEN 'week' " +
//           "                         WHEN :segments = 12 THEN 'month' " +
//           "                         ELSE 'day' END, n.updatedAt) " +
//           "ORDER BY DATE_TRUNC(CASE WHEN :segments = 7 THEN 'day' " +
//           "                         WHEN :segments = 52 THEN 'week' " +
//           "                         WHEN :segments = 12 THEN 'month' " +
//           "                         ELSE 'day' END, n.updatedAt)")
//    List<TimeRangeStatisticDto> getNovelsApprovedByTimeSegments(@Param("startTime") LocalDateTime startTime,
//                                                                @Param("endTime") LocalDateTime endTime,
//                                                                @Param("segments") Integer segments);
//
//    // Classification Statistics
//    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto(ps.name, ps.name, COUNT(n), 'PROGRESS_STATUS') " +
//           "FROM Novel n " +
//           "GROUP BY n.progressStatus")
//    List<NovelClassificationDto> getNovelsByProgressStatus();
//
//    // Interaction Statistics
//    @Query("SELECT SUM(n.totalBookmarks) FROM Novel n WHERE n.isDeleted = false")
//    Long getTotalBookmarks();
//
//    @Query("SELECT SUM(n.totalViews) FROM Novel n WHERE n.isDeleted = false")
//    Long getTotalViews();
//
//    @Query("SELECT SUM(n.totalRates) FROM Novel n WHERE n.isDeleted = false")
//    Long getTotalRatings();
//
//    @Query("SELECT SUM(n.totalComments) FROM Novel n WHERE n.isDeleted = false")
//    Long getTotalComments();
//
//    // Top novels by bookmarks
//    @Query("SELECT new com.example.dto.TopNovelDto(n.id, n.name, n.slug, CAST(n.totalBookmarks AS long), " +
//           "CAST(n.totalViews AS long), n.avgRate, ROW_NUMBER() OVER (ORDER BY n.totalBookmarks DESC)) " +
//           "FROM Novel n WHERE n.isDeleted = false ORDER BY n.totalBookmarks DESC")
//    List<TopNovelDto> getTopNovelsByBookmarks(Pageable pageable);
//
//    // Word count statistics
//    @Query("SELECT SUM(n.wordCount) FROM Novel n WHERE n.isDeleted = false")
//    Long getTotalWordCount();
//
//    // Word count by time range (assuming word count is updated when chapters are added)
//    @Query("SELECT SUM(c.content.length) FROM Chapter c WHERE c.createdAt BETWEEN :startTime AND :endTime AND c.isDeleted = false")
//    Long getWordCountBetween(@Param("startTime") LocalDateTime startTime,
//                            @Param("endTime") LocalDateTime endTime);
//
//    // Novel completion statistics
//    @Query("SELECT new com.example.dto.NovelCompletionDto(n.id, n.name, " +
//           "CAST(COUNT(DISTINCT ur.userId) AS long), " +
//           "CAST(COUNT(DISTINCT CASE WHEN ur.completionRate >= 0.95 THEN ur.userId END) AS long), " +
//           "AVG(CASE WHEN ur.completionRate >= 0.95 THEN 1.0 ELSE 0.0 END), " +
//           "n.totalChapters) " +
//           "FROM Novel n LEFT JOIN UserReadingProgress ur ON n.id = ur.novelId " +
//           "WHERE n.isDeleted = false " +
//           "GROUP BY n.id, n.name, n.totalChapters " +
//           "ORDER BY AVG(CASE WHEN ur.completionRate >= 0.95 THEN 1.0 ELSE 0.0 END) DESC")
//    List<NovelCompletionDto> getNovelCompletionStatistics();
}