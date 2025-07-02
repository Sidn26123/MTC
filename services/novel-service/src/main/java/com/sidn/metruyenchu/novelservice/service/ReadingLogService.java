package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.DailyReadingStat;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogCreateRequest;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadingLogService {

    ReadingLogRepository readingLogRepository;
    ReadingLogMapper readingLogMapper;

    public ReadingLogResponse create(ReadingLogCreateRequest request) {
        ReadingLog log = readingLogMapper.toReadingLog(request);
        log.setReadAt(LocalDateTime.now());
        return readingLogMapper.toReadingLogResponse(readingLogRepository.save(log));
    }

    public List<ReadingLogResponse> getByUser(String userId) {
        return readingLogRepository.findByUserId(userId).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public List<ReadingLogResponse> getByStory(String storyId) {
        return readingLogRepository.findByStoryId(storyId).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public List<ReadingLogResponse> getByUserAndStory(String userId, String storyId) {
        return readingLogRepository.findByUserIdAndStoryId(userId, storyId).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public List<ReadingLogResponse> getLogsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return readingLogRepository.findAllByReadAtBetween(start, end).stream()
                .map(readingLogMapper::toReadingLogResponse)
                .toList();
    }

    public Map<String, Object> statistic(LocalDateTime start, LocalDateTime end) {
        List<ReadingLog> logs = readingLogRepository.findAllByReadAtBetween(start, end);

        long totalLogs = logs.size();
        long totalDuration = logs.stream().mapToLong(ReadingLog::getDuration).sum();
        long finishedCount = logs.stream().filter(ReadingLog::getIsFinished).count();

        return Map.of(
                "totalLogs", totalLogs,
                "totalDurationSeconds", totalDuration,
                "finishedCount", finishedCount
        );
    }

    public List<DailyReadingStat> getDailyStats(LocalDateTime start, LocalDateTime end) {
        return readingLogRepository.getDailyReadingStats(start, end);
    }

    public List<UserReadingStat> getUserStats(LocalDateTime start, LocalDateTime end) {
        return readingLogRepository.getUserReadingStats(start, end);
    }
}

