package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.DailyReadingStat;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.UserReadingStat;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ReadingLogResponse;
import com.sidn.metruyenchu.novelservice.service.ReadingLogService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reading-logs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReadingLogController {
    ReadingLogService readingLogService;

    @PostMapping
    public ApiResponse<ReadingLogResponse> create(@RequestBody @Valid ReadingLogCreateRequest request) {
        return ApiResponse.<ReadingLogResponse>builder()
                .result(readingLogService.create(request))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReadingLogResponse>> getByUser(@PathVariable String userId) {
        return ApiResponse.<List<ReadingLogResponse>>builder()
                .result(readingLogService.getByUser(userId))
                .build();
    }

    @GetMapping("/story/{storyId}")
    public ApiResponse<List<ReadingLogResponse>> getByStory(@PathVariable String storyId) {
        return ApiResponse.<List<ReadingLogResponse>>builder()
                .result(readingLogService.getByStory(storyId))
                .build();
    }

    @GetMapping("/user/{userId}/story/{storyId}")
    public ApiResponse<List<ReadingLogResponse>> getByUserAndStory(
            @PathVariable String userId,
            @PathVariable String storyId) {
        return ApiResponse.<List<ReadingLogResponse>>builder()
                .result(readingLogService.getByUserAndStory(userId, storyId))
                .build();
    }

    @GetMapping("/range")
    public ApiResponse<List<ReadingLogResponse>> getByTimeRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ApiResponse.<List<ReadingLogResponse>>builder()
                .result(readingLogService.getLogsInTimeRange(start, end))
                .build();
    }

    @GetMapping("/statistic")
    public ApiResponse<Map<String, Object>> getStatistic(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ApiResponse.<Map<String, Object>>builder()
                .result(readingLogService.statistic(start, end))
                .build();
    }

    @GetMapping("/statistic/daily")
    public ApiResponse<List<DailyReadingStat>> getDailyStats(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ApiResponse.<List<DailyReadingStat>>builder()
                .result(readingLogService.getDailyStats(start, end))
                .build();
    }

    @GetMapping("/statistic/user")
    public ApiResponse<List<UserReadingStat>> getUserStats(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ApiResponse.<List<UserReadingStat>>builder()
                .result(readingLogService.getUserStats(start, end))
                .build();
    }
}
