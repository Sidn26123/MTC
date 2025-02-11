package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.service.ChapterService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterController {
    ChapterService chapterService;

    @PostMapping("/create")
    ApiResponse<ChapterResponse> createChapter(@Valid @RequestBody ChapterCreationRequest request){
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.createChapter(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ChapterResponse>> getAllChapters(){
        log.info("abc");
        return ApiResponse.<List<ChapterResponse>>builder()
                .result(chapterService.getAllChapters())
                .build();
    }

    @DeleteMapping("/{chapterId}")
    ApiResponse<String> deleteChapter(@PathVariable String chapterId){
        chapterService.deleteChapter(chapterId);
        return ApiResponse.<String>builder()
                .result(String.format("Chapter %s đã xoá thành công", chapterId))
                .build();
    }

    @PutMapping("/{chapterId}")
    ApiResponse<ChapterResponse> updateChapter(@PathVariable String chapterId, @Valid @RequestBody ChapterCreationRequest request){
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.updateChapter(chapterId, request))
                .build();
    }

    @GetMapping("/{chapterId}")
    ApiResponse<ChapterResponse> getChapter(@PathVariable String chapterId){
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.getChapter(chapterId))
                .build();
    }
}
