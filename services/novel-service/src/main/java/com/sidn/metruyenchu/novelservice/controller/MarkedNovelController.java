package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelRequest;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.markedNovel.MarkedNovelResponse;
import com.sidn.metruyenchu.novelservice.service.MarkedNovelService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookshelfs/marked-novels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MarkedNovelController {

    MarkedNovelService markedNovelService;

    /**
     * Tạo mới truyện đã đánh dấu
     * @param request MarkedNovelCreateRequest
     * @return MarkedNovelResponse
     */
    @PostMapping
    public ApiResponse<MarkedNovelResponse> createMarkedNovel(@Valid @RequestBody MarkedNovelCreateRequest request) {
        return ApiResponse.<MarkedNovelResponse>builder()
                .result(markedNovelService.createMarkedNovel(request))
                .build();
    }

    /**
     * Lấy thông tin truyện đã đánh dấu theo ID
     * @param markedNovelId
     * @return MarkedNovelResponse
     */
    @GetMapping("/{markedNovelId}")
    public ApiResponse<MarkedNovelResponse> getMarkedNovelById(@PathVariable String markedNovelId) {
        return ApiResponse.<MarkedNovelResponse>builder()
                .result(markedNovelService.getById(markedNovelId))
                .build();
    }

    /**
     * Lấy tất cả truyện đã đánh dấu (có phân trang)
     * @param request BaseFilterRequest
     * @return PageResponse<MarkedNovelResponse>
     */
    @GetMapping
    public ApiResponse<PageResponse<MarkedNovelResponse>> getAllMarkedNovels(@ModelAttribute BaseFilterRequest request) {
        return ApiResponse.<PageResponse<MarkedNovelResponse>>builder()
                .result(markedNovelService.getAll(request))
                .build();
    }

    /**
     * Lấy tất cả truyện đã đánh dấu của 1 user
     * @param userId ID của user
     * @param page số trang
     * @param size số lượng mỗi trang
     * @return PageResponse<MarkedNovelResponse>
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<PageResponse<MarkedNovelResponse>> getAllMarkedNovelsByUserId(@PathVariable String userId,
                                                                                     @RequestParam(defaultValue = "1") int page,
                                                                                     @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<MarkedNovelResponse>>builder()
                .result(markedNovelService.getAllByUserId(userId, page, size))
                .build();
    }

    /**
     * Cập nhật truyện đã đánh dấu
     * @param markedNovelId ID của truyện đã đánh dấu
     * @param request MarkedNovelUpdateRequest
     * @return MarkedNovelResponse
     */
    @PutMapping("/{markedNovelId}")
    public ApiResponse<MarkedNovelResponse> updateMarkedNovel(@PathVariable String markedNovelId,
                                                              @Valid @RequestBody MarkedNovelUpdateRequest request) {
        return ApiResponse.<MarkedNovelResponse>builder()
                .result(markedNovelService.updateMarkedNovel(markedNovelId, request))
                .build();
    }

    /**
     * Xóa truyện đã đánh dấu
     * @param markedNovelId ID của truyện đã đánh dấu
     * @return ApiResponse<Void>
     */
    @DeleteMapping("/hard/{markedNovelId}")
    public ApiResponse<Void> deleteMarkedNovel(@PathVariable String markedNovelId) {
        markedNovelService.deleteMarkedNovel(markedNovelId);
        return ApiResponse.<Void>builder().build();
    }

    /**
     * Xóa truyện đã đánh dấu (soft)
     * @param markedNovelId ID của truyện đã đánh dấu
     * @return ApiResponse<Void>
     */
    @DeleteMapping("/{markedNovelId}")
    public ApiResponse<Void> softDeleteMarkedNovel(@PathVariable String markedNovelId) {
        markedNovelService.softDeleteMarkedNovel(markedNovelId);
        return ApiResponse.<Void>builder().build();
    }


    /**
     * Đánh dấu truyện
     * @param request MarkedNovelRequest
     * @return MarkedNovelResponse
     */
    @PostMapping("/mark")
    public ApiResponse<MarkedNovelResponse> markNovel(@Valid @RequestBody MarkedNovelRequest request) {
        return ApiResponse.<MarkedNovelResponse>builder()
                .result(markedNovelService.markNovel(request))
                .build();
    }
}
