package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.DailyReadingStat;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.UserReadingStat;

import com.sidn.metruyenchu.novelservice.dto.response.chapter.ReadingLogResponse;
import com.sidn.metruyenchu.novelservice.entity.ReadingLog;
import com.sidn.metruyenchu.novelservice.mapper.ReadingLogMapper;
import com.sidn.metruyenchu.novelservice.repository.ReadingLogRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class ReadingLogService {

    ReadingLogRepository readingLogRepository;
    ReadingLogMapper readingLogMapper;

    @Transactional
    public ReadingLogResponse create(ReadingLogCreateRequest request) {
        ReadingLog log = readingLogMapper.toReadingLog(request);
        // readAt sẽ được set tự động trong @PrePersist
        return readingLogMapper.toReadingLogResponse(readingLogRepository.save(log));
    }

    @Transactional
    public void update(String logId, ReadingLogUpdateRequest request) {
        ReadingLog existingLog = readingLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Reading log not found"));

        // Cập nhật các trường cần thiết
        existingLog.setDuration(request.getDuration());
        existingLog.setProgress(request.getProgress());
        existingLog.setDevice(request.getDevice());
        existingLog.setIpAddress(request.getIpAddress());
        existingLog.setUserAgent(request.getUserAgent());
        existingLog.setIsFinished(request.getIsFinished());

        readingLogRepository.save(existingLog);
    }

    public List<ReadingLogResponse> getByUser(String userId) {
        return readingLogRepository.findByUserId(userId).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public List<ReadingLogResponse> getByNovel(String novelId) {
        return readingLogRepository.findByNovelId(novelId).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public List<ReadingLogResponse> getByUserAndNovel(String userId, String novelId) {
        return readingLogRepository.findByUserIdAndNovelId(userId, novelId).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public List<ReadingLogResponse> getLogsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return readingLogRepository.findByReadAtBetween(start, end).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public Map<String, Object> getStatistics(LocalDateTime start, LocalDateTime end) {
        Object[] stats = readingLogRepository.getOverallStats(start, end);

        Long totalLogs = ((Number) stats[0]).longValue();
        Long totalDuration = ((Number) stats[1]).longValue();
        Long finishedCount = ((Number) stats[2]).longValue();

        return Map.of(
                "totalLogs", totalLogs,
                "totalDurationSeconds", totalDuration,
                "finishedCount", finishedCount,
                "averageDuration", totalLogs > 0 ? (double) totalDuration / totalLogs : 0.0
        );
    }

    public List<DailyReadingStat> getDailyStats(LocalDateTime start, LocalDateTime end) {
        return readingLogRepository.getDailyReadingStats(start, end);
    }

    public List<UserReadingStat> getUserStats(LocalDateTime start, LocalDateTime end) {
        return readingLogRepository.getUserReadingStats(start, end);
    }

    public Optional<LocalDateTime> getNearestReadAt(String userId, String novelId) {
        return readingLogRepository.findTopByUserIdAndNovelIdOrderByReadAtDesc(userId, novelId)
                .map(ReadingLog::getReadAt);
    }

    // Thêm một số phương thức tiện ích
    public boolean hasUserReadChapter(String userId, String chapterId) {
        return readingLogRepository.findByUserId(userId).stream()
                .anyMatch(log -> chapterId.equals(log.getChapterId()) &&
                        Boolean.TRUE.equals(log.getIsFinished()));
    }

    public long countReadingsByNovel(String novelId) {
        return readingLogRepository.findByNovelId(novelId).size();
    }

    public double getAverageProgressByUserAndNovel(String userId, String novelId) {
        List<ReadingLog> logs = readingLogRepository.findByUserIdAndNovelId(userId, novelId);
        return logs.stream()
                .filter(log -> log.getProgress() != null)
                .mapToDouble(ReadingLog::getProgress)
                .average()
                .orElse(0.0);
    }

    public LocalDateTime getNearestUserRead(String userId, String chapterId){
        return null;
    }
}

