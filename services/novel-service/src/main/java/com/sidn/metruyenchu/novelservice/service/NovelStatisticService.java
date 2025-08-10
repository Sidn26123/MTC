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
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            case WEEK -> raw = novelRepository. getCountByWeek(start, end);
            case MONTH -> raw = novelRepository.getCountByMonth(start, end);
            default -> throw new IllegalArgumentException("Unsupported segment type");
        }
        if (raw.isEmpty()) {
            return List.of(new TimeRangeStatisticDto(start, end, 0L));
        }
//        return raw.stream()
//                .map(row -> new TimeRangeStatisticDto(
//                        ((Timestamp) row[0]).toLocalDateTime(),
//                        ((Timestamp) row[1]).toLocalDateTime(),
//                        (Long) row[2]
//                ))
//                .toList();
        List<TimeRangeStatisticDto> existingData = raw.stream()
                .map(row -> new TimeRangeStatisticDto(
                        ((Timestamp) row[0]).toLocalDateTime(),
                        ((Timestamp) row[1]).toLocalDateTime(),
                        (Long) row[2]
                ))
                .toList();

        // Fill missing periods với count = 0
        return fillMissingPeriods(existingData, start, end, unit);
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
//
//        return raw.stream()
//                .map(row -> new TimeRangeStatisticDto(
//                        ((Timestamp) row[0]).toLocalDateTime(),
//                        ((Timestamp) row[1]).toLocalDateTime(),
//                        ((Number) row[2]).longValue()
//                ))
//                .collect(Collectors.toList());
        List<TimeRangeStatisticDto> existingData = raw.stream()
                .map(row -> new TimeRangeStatisticDto(
                        ((Timestamp) row[0]).toLocalDateTime(),
                        ((Timestamp) row[1]).toLocalDateTime(),
                        (Long) row[2]
                ))
                .toList();

        // Fill missing periods với count = 0
        return fillMissingPeriods(existingData, start, end, unit);
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
                        (String) row[3],
                        ((Number) row[4]).longValue(), // bookmarks
                        ((Number) row[5]).longValue(), // views
                        ((Number) row[6]).floatValue(), // avgRate
                        ((Number) row[7]).intValue(),    // rank
                        ((Number) row[8]).intValue()     // totalPromotions
                ))
                .toList();
        return topNovels;
    }

    public List<TopNovelDto> getTopNovelsByPromotions(int limit) {
        List<Object[]> raw = novelRepository.getTopNovelsByPromotions(limit);
        return raw.stream()
                .map(row -> new TopNovelDto(
                        (String) row[0], // novelId
                        (String) row[1], // title
                        (String) row[2], // slug
                        (String) row[3],
                        ((Number) row[4]).longValue(), // bookmarks
                        ((Number) row[5]).longValue(), // views
                        ((Number) row[6]).floatValue(), // avgRate
                        0,    // rank
                        ((Number) row[7]).intValue()     // totalPromotions
                ))
                .collect(Collectors.toList());
    }

    public Long getTotalWordCount() {
        return novelRepository.getTotalWordCount();
    }

    public Long getWordCountBetween(LocalDateTime start, LocalDateTime end) {
        return novelRepository.getWordCountBetween(start, end);
    }
    private List<TimeRangeStatisticDto> fillMissingPeriods(List<TimeRangeStatisticDto> existingData,
                                                           LocalDateTime start,
                                                           LocalDateTime end,
                                                           TimeSegmentUnit unit) {
        // Tạo map để lookup nhanh existing data
        Map<String, TimeRangeStatisticDto> existingMap = existingData.stream()
                .collect(Collectors.toMap(
                        dto -> formatPeriodKey(dto.getStartTime(), unit),
                        dto -> dto
                ));

        List<TimeRangeStatisticDto> result = new ArrayList<>();
        LocalDateTime current = truncateToUnit(start, unit);
        LocalDateTime endTruncated = truncateToUnit(end, unit);

        while (!current.isAfter(endTruncated)) {
            String periodKey = formatPeriodKey(current, unit);
            TimeRangeStatisticDto existing = existingMap.get(periodKey);

            if (existing != null) {
                result.add(existing);
            } else {
                // Tạo period mới với count = 0
                LocalDateTime periodEnd = calculatePeriodEnd(current, unit);
                result.add(new TimeRangeStatisticDto(current, periodEnd, 0L));
            }

            current = addPeriod(current, unit);
        }

        return result;
    }
    private String formatPeriodKey(LocalDateTime dateTime, TimeSegmentUnit unit) {
        return switch (unit) {
            case DAY -> dateTime.toLocalDate().toString();
            case WEEK -> dateTime.with(DayOfWeek.MONDAY).toLocalDate().toString();
            case MONTH -> dateTime.getYear() + "-" + String.format("%02d", dateTime.getMonthValue());
        };
    }

    private LocalDateTime truncateToUnit(LocalDateTime dateTime, TimeSegmentUnit unit) {
        return switch (unit) {
            case DAY -> dateTime.truncatedTo(ChronoUnit.DAYS);
            case WEEK -> dateTime.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
            case MONTH -> dateTime.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
        };
    }

    private LocalDateTime addPeriod(LocalDateTime dateTime, TimeSegmentUnit unit) {
        return switch (unit) {
            case DAY -> dateTime.plusDays(1);
            case WEEK -> dateTime.plusWeeks(1);
            case MONTH -> dateTime.plusMonths(1);
        };
    }

    private LocalDateTime calculatePeriodEnd(LocalDateTime periodStart, TimeSegmentUnit unit) {
        return switch (unit) {
            case DAY -> periodStart.plusDays(1).minusSeconds(1);
            case WEEK -> periodStart.plusWeeks(1).minusSeconds(1);
            case MONTH -> periodStart.plusMonths(1).minusSeconds(1);
        };
    }
}
