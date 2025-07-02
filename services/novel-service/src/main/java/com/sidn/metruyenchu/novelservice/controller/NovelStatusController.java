package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelStatusResponse;
import com.sidn.metruyenchu.novelservice.service.NovelStatusService;
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
@RequestMapping("/novel-status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelStatusController {
    NovelStatusService novelStatusService;

    @PostMapping("/create")
    ApiResponse<NovelStatusResponse> createNovelStatus(@Valid @RequestBody NovelStatusCreationRequest request) {
        return ApiResponse.<NovelStatusResponse>builder()
                .result(novelStatusService.createNovelStatus(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<NovelStatusResponse>> getAllNovelStatus() {
        return ApiResponse.<List<NovelStatusResponse>>builder()
                .result(novelStatusService.getAllNovelStatus())
                .build();
    }

    @GetMapping("/{novelStatusId}")
    ApiResponse<NovelStatusResponse> getNovelStatusById(@PathVariable String novelStatusId) {
        return ApiResponse.<NovelStatusResponse>builder()
                .result(novelStatusService.getNovelStatusById(novelStatusId))
                .build();
    }

    @DeleteMapping("/{novelStatusId}")
    ApiResponse<String> deleteNovelStatus(@PathVariable String novelStatusId) {
        novelStatusService.deleteNovelStatus(novelStatusId);
        return ApiResponse.<String>builder()
                .result(String.format("Novel Status %s đã xoá thành công", novelStatusId))
                .build();
    }

    @PutMapping("/{novelStatusId}")
    ApiResponse<NovelStatusResponse> updateNovelStatus(@Valid @RequestBody NovelStatusCreationRequest request, @PathVariable String novelStatusId) {
        return ApiResponse.<NovelStatusResponse>builder()
                .result(novelStatusService.updateNovelStatus(novelStatusId, request))
                .build();
    }

    @GetMapping("/activatingList")
    ApiResponse<List<NovelStatusResponse>> getNovelStatusByName(@RequestParam String name) {
        return ApiResponse.<List<NovelStatusResponse>>builder()
                .result(novelStatusService.getActivatingList())
                .build();
    }

}
