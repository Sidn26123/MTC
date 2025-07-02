package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.DailyReadingStat;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.UserReadingStat;
import com.sidn.metruyenchu.novelservice.entity.ReadingLog;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface ReadingLogRepository extends MongoRepository<ReadingLog, String> {
    List<ReadingLog> findByUserId(String userId);

    @Query(value = "{ 'readAt' : { $gte: ?0, $lte: ?1 } }")
    List<ReadingLog> findAllByReadAtBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "{ 'userId': ?0, 'storyId': ?1 }")
    List<ReadingLog> findByUserIdAndStoryId(String userId, String storyId);

    List<ReadingLog> findByStoryId(String storyId);

    @Aggregation(pipeline = {
            "{ $match: { readAt: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: { $dateToString: { format: '%Y-%m-%d', date: '$readAt' } }, " +
                    "totalLogs: { $sum: 1 }, " +
                    "totalDuration: { $sum: '$duration' }, " +
                    "finishedCount: { $sum: { $cond: ['$isFinished', 1, 0] } } } }",
            "{ $sort: { _id: 1 } }"
    })
    List<DailyReadingStat> getDailyReadingStats(LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ $match: { readAt: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: '$userId', " +
                    "totalLogs: { $sum: 1 }, " +
                    "totalDuration: { $sum: '$duration' }, " +
                    "finishedCount: { $sum: { $cond: ['$isFinished', 1, 0] } } } }",
            "{ $sort: { totalLogs: -1 } }"
    })
    List<UserReadingStat> getUserReadingStats(LocalDateTime start, LocalDateTime end);
}

