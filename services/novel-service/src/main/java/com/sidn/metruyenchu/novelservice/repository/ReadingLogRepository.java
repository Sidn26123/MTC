package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.DailyReadingStat;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.UserReadingStat;
import com.sidn.metruyenchu.novelservice.entity.ReadingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReadingLogRepository extends JpaRepository<ReadingLog, String> {

    List<ReadingLog> findByUserId(String userId);

    List<ReadingLog> findByReadAtBetween(LocalDateTime start, LocalDateTime end);

    List<ReadingLog> findByUserIdAndNovelId(String userId, String novelId);

    List<ReadingLog> findByNovelId(String novelId);

    // Query để lấy thống kê theo ngày
    @Query(value = """
        SELECT DATE(read_at) as date,
               COUNT(*) as totalLogs,
               COALESCE(SUM(duration), 0) as totalDuration,
               SUM(CASE WHEN is_finished = true THEN 1 ELSE 0 END) as finishedCount
        FROM reading_logs 
        WHERE read_at BETWEEN :startDate AND :endDate
        GROUP BY DATE(read_at)
        ORDER BY DATE(read_at)
        """, nativeQuery = true)
    List<Object[]> getDailyReadingStatsNative(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    // Query để lấy thống kê theo user
    @Query(value = """
        SELECT user_id as userId,
               COUNT(*) as totalLogs,
               COALESCE(SUM(duration), 0) as totalDuration,
               SUM(CASE WHEN is_finished = true THEN 1 ELSE 0 END) as finishedCount
        FROM reading_logs 
        WHERE read_at BETWEEN :startDate AND :endDate
        GROUP BY user_id
        ORDER BY COUNT(*) DESC
        """, nativeQuery = true)
    List<Object[]> getUserReadingStatsNative(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    // JPQL query thay thế cho MongoDB aggregation
    @Query("""
        SELECT new com.sidn.metruyenchu.novelservice.dto.response.DailyReadingStat(
            CAST(rl.readAt AS DATE),
            COUNT(rl),
            COALESCE(SUM(rl.duration), 0),
            SUM(CASE WHEN rl.isFinished = true THEN 1L ELSE 0L END)
        )
        FROM ReadingLog rl
        WHERE rl.readAt BETWEEN :startDate AND :endDate
        GROUP BY CAST(rl.readAt AS DATE)
        ORDER BY CAST(rl.readAt AS DATE)
        """)
    List<DailyReadingStat> getDailyReadingStats(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT new com.sidn.metruyenchu.novelservice.dto.response.UserReadingStat(
            rl.userId,
            COUNT(rl),
            COALESCE(SUM(rl.duration), 0),
            SUM(CASE WHEN rl.isFinished = true THEN 1L ELSE 0L END)
        )
        FROM ReadingLog rl
        WHERE rl.readAt BETWEEN :startDate AND :endDate
        GROUP BY rl.userId
        ORDER BY COUNT(rl) DESC
        """)
    List<UserReadingStat> getUserReadingStats(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    // Lấy lần đọc gần nhất của user cho novel
    Optional<ReadingLog> findTopByUserIdAndNovelIdOrderByReadAtDesc(String userId, String novelId);

    // Thống kê tổng quan
    @Query("""
        SELECT COUNT(rl), 
               COALESCE(SUM(rl.duration), 0), 
               SUM(CASE WHEN rl.isFinished = true THEN 1L ELSE 0L END)
        FROM ReadingLog rl
        WHERE rl.readAt BETWEEN :startDate AND :endDate
        """)
    Object[] getOverallStats(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);

    Optional<ReadingLog> findTopByUserIdAndChapterIdOrderByReadAtDesc(String userId, String chapterId);

    // Lấy tất cả lần đọc của user cho chapter cụ thể, sắp xếp theo thời gian gần nhất
    List<ReadingLog> findByUserIdAndChapterIdOrderByReadAtDesc(String userId, String chapterId);

    // Query tùy chỉnh để lấy lần đọc gần nhất với điều kiện bổ sung
    @Query("""
        SELECT rl FROM ReadingLog rl 
        WHERE rl.userId = :userId AND rl.chapterId = :chapterId
        ORDER BY rl.readAt DESC
        LIMIT 1
        """)
    Optional<ReadingLog> findLatestReadingByUserAndChapter(@Param("userId") String userId,
                                                           @Param("chapterId") String chapterId);

}