package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.response.CommentStatsResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.stat.*;
import com.sidn.metruyenchu.feedbackservice.enums.TimeRangeType;
import com.sidn.metruyenchu.feedbackservice.service.StatsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.*;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentStatsResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.stat.AuthorCommentStatsResponse;
import com.sidn.metruyenchu.feedbackservice.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StatsController {
    CommentService commentService;

    @GetMapping("/comment")
    public AuthorCommentStatsResponse getStats(
            @RequestParam List<String> novelIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return commentService.getStats(novelIds, from, to);
    }

    @GetMapping("/comment/monthly")
    public List<CommentStatsResponse> getMonthlyStats(
            @RequestParam List<String> novelIds,
            @RequestParam int year
    ) {
        return commentService.getMonthlyStats(novelIds, year);
    }

    @GetMapping("/comment/daily")
    public List<CommentStatsResponse> getDailyStats(
            @RequestParam List<String> novelIds,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return commentService.getDailyStats(novelIds, year, month);
    }

    /**
     * Biểu đồ tròn: Tỉ trọng top các truyện có lượt comment nhiều nhất
     */
    @PostMapping("/novels/top-commented/pie-chart")
    public ApiResponse<List<NovelCommentPieChartDto>> getTopCommentedNovelsPieChart(
            @RequestBody @Valid TopCommentedNovelsRequest request) {

        log.info("Getting pie chart for novels: {}, limit: {}", request.getNovelIds(), request.getLimit());

        List<NovelCommentPieChartDto> result = commentService
                .getTopCommentedNovelsPieChart(request.getNovelIds(), request.getLimit());

        return ApiResponse.<List<NovelCommentPieChartDto>>builder()
                .result(result)
                .build();
    }

    /**
     * Biểu đồ cột: Lượt comment theo thời gian (tổng các truyện)
     */
    @PostMapping("/novels/timeline/bar-chart")
    public ApiResponse<List<CommentTimelineDto>> getCommentsTimelineForNovels(
            @RequestBody @Valid CommentTimelineRequest request) {

        log.info("Getting timeline for novels: {}, range: {}, from {} to {}",
                request.getNovelIds(), request.getTimeRange(), request.getStartDate(), request.getEndDate());

        List<CommentTimelineDto> result = commentService
                .getCommentsTimelineForNovels(
                        request.getNovelIds(),
                        request.getTimeRange(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        return ApiResponse.<List<CommentTimelineDto>>builder()
                .result(result)
                .build();
    }

    /**
     * Biểu đồ cột: Lượt comment theo thời gian của 1 truyện (time range linh hoạt)
     */
    @GetMapping("/novels/{novelId}/timeline/bar-chart")
    public ApiResponse<List<CommentTimelineDto>> getSingleNovelCommentsTimeline(
            @PathVariable String novelId,
            @RequestParam TimeRangeType timeRange,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Getting timeline for novel: {}, range: {}, from {} to {}",
                novelId, timeRange, startDate, endDate);

        List<CommentTimelineDto> result = commentService
                .getSingleNovelCommentsTimeline(novelId, timeRange, startDate, endDate);

        return ApiResponse.<List<CommentTimelineDto>>builder()
                .result(result)
                .build();
    }

    /**
     * Biểu đồ cột: Lượt comment theo giờ trong ngày của 1 truyện
     */
    @GetMapping("/novels/{novelId}/daily-hourly/bar-chart")
    public ApiResponse<List<CommentTimelineDto>> getDailyHourlyCommentsForNovel(
            @PathVariable String novelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        log.info("Getting hourly comments for novel: {} on date: {}", novelId, date);

        List<CommentTimelineDto> result = commentService
                .getDailyHourlyCommentsForNovel(novelId, date);

        return ApiResponse.<List<CommentTimelineDto>>builder()
                .result(result)
                .build();
    }

}
