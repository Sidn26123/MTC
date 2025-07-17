package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopNovelDto;
import com.sidn.metruyenchu.novelservice.enums.TimeSegmentUnit;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelStatisticsRepository;
import com.sidn.metruyenchu.shared_library.dto.request.TimeRangeStatisticDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class NovelStatisticService {
    NovelStatisticsRepository novelRepository;
    public List<TimeRangeStatisticDto> getCreatedSegmentStat(LocalDateTime start, LocalDateTime end, TimeSegmentUnit unit) {
        List<Object[]> raw;
        log.info("accessing novel statistics from {} to {} with unit {}", start, end, unit);
        switch (unit) {
            case DAY -> raw = novelRepository.getCountByDay(start, end);
            case WEEK -> raw = novelRepository.getCountByWeek(start, end);
            case MONTH -> raw = novelRepository.getCountByMonth(start, end);
            default -> throw new IllegalArgumentException("Unsupported segment type");
        }

        return raw.stream()
                .map(row -> new TimeRangeStatisticDto(
                        ((Timestamp) row[0]).toLocalDateTime(),
                        ((Timestamp) row[1]).toLocalDateTime(),
                        (Long) row[2]
                ))
                .toList();
    }
    public List<TimeRangeStatisticDto> getApprovedSegmentStat(LocalDateTime start, LocalDateTime end, TimeSegmentUnit unit) {
        List<Object[]> raw;
        log.info("Accessing approved novel statistics from {} to {} with unit {}", start, end, unit);
        switch (unit) {
            case DAY -> raw = novelRepository.getApprovedCountByDay(start, end);
            case WEEK -> raw = novelRepository.getApprovedCountByWeek(start, end);
            case MONTH -> raw = novelRepository.getApprovedCountByMonth(start, end);
            default -> throw new IllegalArgumentException("Unsupported segment type");
        }

        return raw.stream()
                .map(row -> new TimeRangeStatisticDto(
                        ((Timestamp) row[0]).toLocalDateTime(),
                        ((Timestamp) row[1]).toLocalDateTime(),
                        ((Number) row[2]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<NovelClassificationDto> getByProgressStatus() {
        return novelRepository.getNovelsByProgressStatus();
    }

    public Long getTotalBookmarks() {
        return novelRepository.getTotalBookmarks();
    }

    public Long getTotalViews() {
        return novelRepository.getTotalViews();
    }

    public Long getTotalRatings() {
        return novelRepository.getTotalRatings();
    }

    public Long getTotalComments() {
        return novelRepository.getTotalComments();
    }

    public List<TopNovelDto> getTopNovelsByBookmarks(int limit) {
        List<Object[]> raw = novelRepository.getTopNovelsWithRank(limit);
        List<TopNovelDto> topNovels = raw.stream()
                .map(row -> new TopNovelDto(
                        (String) row[0], // novelId
                        (String) row[1], // title
                        (String) row[2], // slug
                        ((Number) row[3]).longValue(), // bookmarks
                        ((Number) row[4]).longValue(), // views
                        ((Number) row[5]).floatValue(), // avgRate
                        ((Number) row[6]).intValue()    // rank
                ))
                .toList();
        return topNovels;
    }

    public Long getTotalWordCount() {
        return novelRepository.getTotalWordCount();
    }

    public Long getWordCountBetween(LocalDateTime start, LocalDateTime end) {
        return novelRepository.getWordCountBetween(start, end);
    }
}
