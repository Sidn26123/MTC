package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.ChapterViewDistributionDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopChapterDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopNovelDto;
import com.sidn.metruyenchu.novelservice.enums.TimeSegmentUnit;
import com.sidn.metruyenchu.novelservice.service.ChapterStatisticService;
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

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/chapter-statistic")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class ChapterStatisticController {

    ChapterStatisticService chapterStatisticService;

    @GetMapping("/top/views")
    public ApiResponse<List<TopChapterDto>> getTopChaptersByViews(
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<List<TopChapterDto>>builder()
                .result(chapterStatisticService.getTopChaptersByViews(limit))
                .build();
    }

    @GetMapping("/distribution/views")
    public ApiResponse<List<ChapterViewDistributionDto>> getChapterViewDistribution() {
        return ApiResponse.<List<ChapterViewDistributionDto>>builder()
                .result(chapterStatisticService.getChapterViewDistribution())
                .build();
    }

    @GetMapping("/comments/range")
    public ApiResponse<Long> getTotalChapterCommentsBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        return ApiResponse.<Long>builder()
                .result(chapterStatisticService.getTotalChapterCommentsBetween(
                        startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)))
                .build();
    }
}
