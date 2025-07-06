package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.enums.TimeSegmentUnit;
import com.sidn.metruyenchu.novelservice.service.NovelStatisticService;
import com.sidn.metruyenchu.shared_library.dto.request.TimeRangeStatisticDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.sidn.metruyenchu.shared_library.utils.DatetimeUtils.fromString;

@RestController
@RequestMapping("/novel-statistic")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelStatisticController {
    NovelStatisticService novelStatisticService;
    @GetMapping("")
    public ApiResponse<List<TimeRangeStatisticDto>> getNovelStatistics(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam String segmentType
    ) {
        log.info("Fetching novel statistics from {} to {} with segment type {}", startDate, endDate, segmentType);

        return ApiResponse.<List<TimeRangeStatisticDto>>builder()
                .result(novelStatisticService.getCreatedSegmentStat(
                        startDate.atStartOfDay(),
                        endDate.atTime(LocalTime.MAX),
                        TimeSegmentUnit.valueOf(segmentType)
                ))
                .build();
    }
}
