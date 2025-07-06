package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.enums.TimeSegmentUnit;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelStatisticsRepository;
import com.sidn.metruyenchu.shared_library.dto.request.TimeRangeStatisticDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
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
}
