package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.response.category.CategoryResponse;
import com.sidn.metruyenchu.novelservice.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {

    CategoryService categoryService;

    /**
     * API lấy danh sách trạng thái tiến độ của truyện.
     */
    @GetMapping("/novel-progress-status")
    public ApiResponse<List<CategoryResponse>> getNovelProgressStatus() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getNovelProgressStatus())
                .build();
    }

    /**
     * API lấy danh sách thuộc tính của truyện.
     */
    @GetMapping("/novel-attributes")
    public ApiResponse<List<CategoryResponse>> getNovelAttributes() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getNovelAttributes())
                .build();
    }

    /**
     * API lấy danh sách trạng thái truyện.
     */
    @GetMapping("/novel-state")
    public ApiResponse<List<CategoryResponse>> getNovelState() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getNovelState())
                .build();
    }

    /**
     * API lấy danh sách chế độ hiển thị của truyện.
     */
    @GetMapping("/novel-visibility")
    public ApiResponse<List<CategoryResponse>> getNovelVisibility() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getNovelVisibility())
                .build();
    }

    /**
     * API lấy danh sách thể loại truyện.
     */
    @GetMapping("/genres")
    public ApiResponse<List<CategoryResponse>> getGenresList() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getGenresList())
                .build();
    }

    /**
     * API lấy danh sách đặc điểm nhân vật chính.
     */
    @GetMapping("/main-character-traits")
    public ApiResponse<List<CategoryResponse>> getMainCharacterList() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getMainCharacterList())
                .build();
    }

    /**
     * API lấy danh sách môn phái.
     */
    @GetMapping("/sects")
    public ApiResponse<List<CategoryResponse>> getSectList() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getSectList())
                .build();
    }

    /**
     * API lấy danh sách bối cảnh thế giới truyện.
     */
    @GetMapping("/world-scenes")
    public ApiResponse<List<CategoryResponse>> getWorldSceneList() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getWorldSceneList())
                .build();
    }

    /**
     * API lấy danh sách loại truyện.
     */
    @GetMapping("/novel-types")
    public ApiResponse<List<CategoryResponse>> getNovelTypes() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getNovelType())
                .build();
    }
}
