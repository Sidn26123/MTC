package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelAuthorCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelAuthorResponse;
import com.sidn.metruyenchu.novelservice.service.NovelAuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/novel-authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelAuthorController {
    NovelAuthorService novelAuthorService;

    @PostMapping("/create")
    ApiResponse<NovelAuthorResponse> createNovelAuthor(@Valid @RequestBody NovelAuthorCreationRequest request) {
        return ApiResponse.<NovelAuthorResponse>builder()
                .result(novelAuthorService.createNovelAuthor(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<NovelAuthorResponse>> getAllNovelAuthor() {
        return ApiResponse.<List<NovelAuthorResponse>>builder()
                .result(novelAuthorService.getAllNovelAuthor())
                .build();
    }

    @GetMapping("/{novelAuthorId}")
    ApiResponse<NovelAuthorResponse> getNovelAuthorById(@PathVariable String novelAuthorId) {
        return ApiResponse.<NovelAuthorResponse>builder()
                .result(novelAuthorService.getNovelAuthorById(novelAuthorId))
                .build();
    }

    @DeleteMapping("/{novelAuthorId}")
    ApiResponse<String> deleteNovelAuthor(@PathVariable String novelAuthorId) {
        novelAuthorService.deleteNovelAuthor(novelAuthorId);
        return ApiResponse.<String>builder()
                .result(String.format("Novel Author %s đã xoá thành công", novelAuthorId))
                .build();
    }

    @PutMapping("/{novelAuthorId}")
    ApiResponse<NovelAuthorResponse> updateNovelAuthor(@Valid @RequestBody NovelAuthorCreationRequest request, @PathVariable String novelAuthorId) {
        return ApiResponse.<NovelAuthorResponse>builder()
                .result(novelAuthorService.updateNovelAuthor(novelAuthorId, request))
                .build();
    }

    @GetMapping("/{name}")
    ApiResponse<List<NovelAuthorResponse>> getNovelAuthorByName(@RequestParam String name) {
        return ApiResponse.<List<NovelAuthorResponse>>builder()
                .result(novelAuthorService.getNovelAuthorByName(name))
                .build();
    }

    @GetMapping("/activatingList")
    ApiResponse<List<NovelAuthorResponse>> getActivatingList() {
        return ApiResponse.<List<NovelAuthorResponse>>builder()
                .result(novelAuthorService.getActivatingList())
                .build();
    }

}
