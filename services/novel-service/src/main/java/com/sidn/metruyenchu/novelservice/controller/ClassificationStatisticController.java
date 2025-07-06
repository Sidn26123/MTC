package com.sidn.metruyenchu.novelservice.controller;
import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopNovelDto;
import com.sidn.metruyenchu.novelservice.enums.TimeSegmentUnit;
import com.sidn.metruyenchu.novelservice.service.ClassificationStatisticService;
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
@RequestMapping("/novel-classification")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClassificationStatisticController {

    ClassificationStatisticService classificationStatisticService;

    @GetMapping("/character-trait")
    public ApiResponse<List<NovelClassificationDto>> getByCharacterTraits() {
        return ApiResponse.<List<NovelClassificationDto>>builder()
                .result(classificationStatisticService.getNovelsByCharacterTrait())
                .build();
    }

    @GetMapping("/genre")
    public ApiResponse<List<NovelClassificationDto>> getByGenres() {
        return ApiResponse.<List<NovelClassificationDto>>builder()
                .result(classificationStatisticService.getNovelsByGenre())
                .build();
    }

    @GetMapping("/sect")
    public ApiResponse<List<NovelClassificationDto>> getBySects() {
        return ApiResponse.<List<NovelClassificationDto>>builder()
                .result(classificationStatisticService.getNovelsBySect())
                .build();
    }
}
