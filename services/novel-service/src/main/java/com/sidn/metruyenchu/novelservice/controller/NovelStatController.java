package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.enums.NovelStat;
import com.sidn.metruyenchu.novelservice.service.NovelStatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/novel-stat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelStatController {

    NovelStatService novelStatService;

    // === TOTAL_COMMENTS ===
    @PutMapping("/{novelId}/comments/increase")
    ApiResponse<Void> increaseTotalComments(@PathVariable String novelId) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.increaseStat(novelId, NovelStat.TOTAL_COMMENTS))
                .build();
    }

    @PostMapping("/{novelId}/rate")
    ApiResponse<Void> rateNovel(@PathVariable String novelId, @RequestParam Float rate) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.rateNovel(novelId, rate))
                .build();
    }

    @PutMapping("/{novelId}/comments/decrease")
    ApiResponse<Void> decreaseTotalComments(@PathVariable String novelId) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.decreaseStat(novelId, NovelStat.TOTAL_COMMENTS))
                .build();
    }

    // === TOTAL_RATES ===
    @PutMapping("/{novelId}/rates/increase")
    ApiResponse<Void> increaseTotalRates(@PathVariable String novelId) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.increaseStat(novelId, NovelStat.TOTAL_RATES))
                .build();
    }

    @PutMapping("/{novelId}/rates/decrease")
    ApiResponse<Void> decreaseTotalRates(@PathVariable String novelId) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.decreaseStat(novelId, NovelStat.TOTAL_RATES))
                .build();
    }

    // === CHANGE FIELDS ===

    @PutMapping("/{novelId}/avg-rate")
    ApiResponse<Void> updateAvgRate(@PathVariable String novelId, @RequestParam Float value) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.changeStat(novelId, NovelStat.AVG_RATE, value))
                .build();
    }

    @PutMapping("/{novelId}/word-count")
    ApiResponse<Void> updateWordCount(@PathVariable String novelId, @RequestParam Integer value) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.changeStat(novelId, NovelStat.WORD_COUNT, value))
                .build();
    }

    @PutMapping("/{novelId}/read-to-comment")
    ApiResponse<Void> updateChapterReadToComment(@PathVariable String novelId, @RequestParam Integer value) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.changeStat(novelId, NovelStat.READ_TO_COMMENT, value))
                .build();
    }

    @PutMapping("/{novelId}/read-to-rate")
    ApiResponse<Void> updateChapterReadToRate(@PathVariable String novelId, @RequestParam Integer value) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.changeStat(novelId, NovelStat.READ_TO_RATE, value))
                .build();
    }

    @PutMapping("/{novelId}/discount")
    ApiResponse<Void> updateFullSetDiscount(@PathVariable String novelId, @RequestParam Integer value) {
        return ApiResponse.<Void>builder()
                .result(novelStatService.changeStat(novelId, NovelStat.FULL_SET_PURCHASE_DISCOUNT, value))
                .build();
    }
}
