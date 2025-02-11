package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.NovelResponse;
import com.sidn.metruyenchu.novelservice.service.NovelService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelController {
    NovelService novelService;

    @PostMapping("/create")
    ApiResponse<NovelResponse> createNovel(@Valid @RequestBody NovelCreationRequest request) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.createNovel(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<NovelResponse>> getAllNovels() {
        return ApiResponse.<List<NovelResponse>>builder()
                .result(novelService.getAllNovel())
                .build();
    }

    @GetMapping("/{novelSlug}")
    ApiResponse<NovelResponse> getNovel(@PathVariable String novelSlug) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.getNovelBySlug(novelSlug))
                .build();
    }

    @PutMapping("/{novelId}")
    ApiResponse<NovelResponse> updateNovel(@PathVariable String novelId, @Valid @RequestBody NovelUpdateRequest request) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.updateNovel(novelId, request))
                .build();
    }

    @DeleteMapping("/{novelId}")
    ApiResponse<String> deleteNovel(@PathVariable String novelId) {
        novelService.deleteNovel(novelId);
        return ApiResponse.<String>builder()
                .result(String.format("Novel %s đã xoá thành công", novelId))
                .build();
    }

//    @GetMapping("/search")
//    ApiResponse<List<NovelResponse>> searchNovel(@RequestParam String keyword){
//
//    }

    @GetMapping("/{novelId}/chapters")
    void getChapters(@PathVariable String novelId){
    }

//    @GetMapping("/{novelId}/chapters/{chapterId}")

    @GetMapping("/get-novel-by-author/{authorId}")
    ApiResponse<List<NovelResponse>> getNovelByAuthor(@PathVariable String authorId) {
        return ApiResponse.<List<NovelResponse>>builder()
                .result(novelService.getNovelByAuthorId(authorId))
                .build();
    }
}
