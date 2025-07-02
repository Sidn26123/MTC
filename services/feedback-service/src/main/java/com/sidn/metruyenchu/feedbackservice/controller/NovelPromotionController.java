package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion.NovelPromotionCreateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion.NovelPromotionUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.novelPromotion.NovelPromotionResponse;
import com.sidn.metruyenchu.feedbackservice.service.NovelPromotionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novel-promotions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelPromotionController {

    NovelPromotionService novelPromotionService;

    @GetMapping
    public ApiResponse<PageResponse<NovelPromotionResponse>> getAllNovelPromotions(@ModelAttribute BaseFilterRequest request) {
        return ApiResponse.<PageResponse<NovelPromotionResponse>>builder()
                .result(novelPromotionService.getAllNovelPromotions(request))
                .build();
    }

    @PostMapping
    public ApiResponse<NovelPromotionResponse> createNovelPromotion(@Valid @RequestBody NovelPromotionCreateRequest request) {
        return ApiResponse.<NovelPromotionResponse>builder()
                .result(novelPromotionService.createNovelPromotion(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NovelPromotionResponse> getNovelPromotion(@PathVariable("id") String id) {
        return ApiResponse.<NovelPromotionResponse>builder()
                .result(novelPromotionService.getNovelPromotion(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteNovelPromotion(@PathVariable("id") String id) {
        novelPromotionService.deleteNovelPromotion(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<NovelPromotionResponse> updateNovelPromotion(@PathVariable("id") String id, @RequestBody NovelPromotionUpdateRequest request) {
        return ApiResponse.<NovelPromotionResponse>builder()
                .result(novelPromotionService.updateNovelPromotion(id, request))
                .build();
    }

    @GetMapping("/novel/{novelId}")
    public ApiResponse<PageResponse<NovelPromotionResponse>> getAllPromotionsByNovelId(@PathVariable("novelId") String novelId, @ModelAttribute BaseFilterRequest request) {
        return ApiResponse.<PageResponse<NovelPromotionResponse>>builder()
                .result(novelPromotionService.getAllPromotionsByNovelId(novelId, request))
                .build();
    }
}
