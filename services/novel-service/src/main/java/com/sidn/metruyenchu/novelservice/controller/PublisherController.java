package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import com.sidn.metruyenchu.novelservice.service.ChapterService;
import com.sidn.metruyenchu.novelservice.service.NovelService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller tổng hợp các endpoint phục vụ cho người đăng truyện:
 * - Quản lý truyện
 * - Quản lý chương
 * - Thống kê, phân tích
 * - Lọc và tìm kiếm
 */
//@RestController
//@RequestMapping("/author")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class AuthorController {
//
//    NovelService novelService;
//    ChapterService chapterService;
//    AnalyticsService analyticsService;
//
//    // ========================== NOVEL =============================
//
//    /**
//     * Tạo truyện mới
//     */
//    @PostMapping("/novels")
//    public ApiResponse<NovelResponse> createNovel(@RequestBody @Valid NovelCreateRequest request) {
//        return ApiResponse.success(novelService.createNovel(request));
//    }
//
//    /**
//     * Cập nhật thông tin truyện
//     */
//    @PutMapping("/novels/{novelId}")
//    public ApiResponse<NovelResponse> updateNovel(@PathVariable String novelId, @RequestBody @Valid NovelUpdateRequest request) {
//        return ApiResponse.success(novelService.updateNovel(novelId, request));
//    }
//
//    /**
//     * Xoá truyện
//     */
//    @DeleteMapping("/novels/{novelId}")
//    public ApiResponse<String> deleteNovel(@PathVariable String novelId) {
//        novelService.deleteNovel(novelId);
//        return ApiResponse.success("Truyện đã xoá thành công");
//    }
//
//    /**
//     * Lấy danh sách truyện của người dùng hiện tại
//     */
//    @GetMapping("/novels/my")
//    public ApiResponse<PageResponse<NovelResponse>> getMyNovels(@RequestParam int page, @RequestParam int size) {
//        return ApiResponse.success(novelService.getMyNovels(page, size));
//    }
//
//    /**
//     * Đổi trạng thái truyện (ẩn, công khai, hoàn thành...)
//     */
//    @PatchMapping("/novels/{novelId}/status")
//    public ApiResponse<NovelResponse> changeNovelStatus(@PathVariable String novelId, @RequestBody ChangeStatusRequest request) {
//        return ApiResponse.success(novelService.changeStatus(novelId, request));
//    }
//
//    // ========================== CHAPTER =============================
//
//    /**
//     * Tạo chương mới cho một truyện
//     */
//    @PostMapping("/novels/{novelId}/chapters")
//    public ApiResponse<ChapterResponse> createChapter(@PathVariable String novelId, @RequestBody @Valid ChapterCreateRequest request) {
//        return ApiResponse.success(chapterService.createChapter(novelId, request));
//    }
//
//    /**
//     * Cập nhật chương truyện
//     */
//    @PutMapping("/chapters/{chapterId}")
//    public ApiResponse<ChapterResponse> updateChapter(@PathVariable String chapterId, @RequestBody @Valid ChapterUpdateRequest request) {
//        return ApiResponse.success(chapterService.updateChapter(chapterId, request));
//    }
//
//    /**
//     * Xoá chương truyện
//     */
//    @DeleteMapping("/chapters/{chapterId}")
//    public ApiResponse<String> deleteChapter(@PathVariable String chapterId) {
//        chapterService.deleteChapter(chapterId);
//        return ApiResponse.success("Chương đã xoá thành công");
//    }
//
//    /**
//     * Danh sách chương theo truyện
//     */
//    @GetMapping("/novels/{novelId}/chapters")
//    public ApiResponse<PageResponse<ChapterResponse>> getChapters(@PathVariable String novelId, @RequestParam int page, @RequestParam int size) {
//        return ApiResponse.success(chapterService.getChaptersByNovel(novelId, page, size));
//    }
//
//    /**
//     * Lấy nội dung chương
//     */
//    @GetMapping("/chapters/{chapterId}/content")
//    public ApiResponse<String> getChapterContent(@PathVariable String chapterId) {
//        return ApiResponse.success(chapterService.getChapterContent(chapterId));
//    }
//
//    /**
//     * Ghim chương
//     */
//    @PatchMapping("/chapters/{chapterId}/pin")
//    public ApiResponse<Void> pinChapter(@PathVariable String chapterId) {
//        chapterService.pinChapter(chapterId);
//        return ApiResponse.success(null);
//    }
//
//    // ========================== THỐNG KÊ =============================
//
//    /**
//     * Tổng lượt xem truyện
//     */
//    @GetMapping("/novels/{novelId}/views")
//    public ApiResponse<Long> getNovelViews(@PathVariable String novelId) {
//        return ApiResponse.success(analyticsService.getNovelViews(novelId));
//    }
//
//    /**
//     * Lượt xem chương
//     */
//    @GetMapping("/chapters/{chapterId}/views")
//    public ApiResponse<Long> getChapterViews(@PathVariable String chapterId) {
//        return ApiResponse.success(analyticsService.getChapterViews(chapterId));
//    }
//
//    /**
//     * Thống kê tổng quan của người đăng truyện
//     */
//    @GetMapping("/me/statistics")
//    public ApiResponse<AuthorStatisticsResponse> getAuthorStatistics() {
//        return ApiResponse.success(analyticsService.getAuthorStatistics());
//    }
//
//    // ========================== FILTER =============================
//
//    /**
//     * Lọc truyện đã đăng
//     */
//    @PostMapping("/novels/filter")
//    public ApiResponse<PageResponse<NovelResponse>> filterMyNovels(@RequestBody NovelFilterRequest request) {
//        return ApiResponse.success(novelService.filterMyNovels(request));
//    }
//
//    /**
//     * Lọc chương đã đăng
//     */
//    @PostMapping("/chapters/filter")
//    public ApiResponse<PageResponse<ChapterResponse>> filterMyChapters(@RequestBody ChapterFilterRequest request) {
//        return ApiResponse.success(chapterService.filterMyChapters(request));
//    }
//}
