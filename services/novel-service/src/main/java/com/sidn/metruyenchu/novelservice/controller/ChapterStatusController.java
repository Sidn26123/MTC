package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.service.ChapterStatusService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter-status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterStatusController {
    ChapterStatusService chapterStatusService;

    @PostMapping("/create")
    ApiResponse<ChapterStatusResponse> createChapterStatus(@Valid @RequestBody ChapterStatusCreationRequest request){
//        ApiResponse<ChapterStatusResponse> createChapterStatusResponse = new ApiResponse<>();
//        createChapterStatusResponse.setResult(
//                chapterStatusService.createChapterStatus(request)
//        );
        return ApiResponse.<ChapterStatusResponse>builder()
                .result(chapterStatusService.createChapterStatus(request))
                .build();
    }



    @GetMapping
    ApiResponse<List<ChapterStatusResponse>> getAllChapterStatus(){
        ApiResponse<ChapterStatusResponse> chapterStatusResponse = new ApiResponse<>();
        return ApiResponse.<List<ChapterStatusResponse>>builder()
                .result(chapterStatusService.getAllChapterStatus())
                .build();
    }

    @DeleteMapping("/{chapterStatusId}")
    ApiResponse<String> deleteChapterStatus(@PathVariable String chapterStatusId){
        chapterStatusService.deleteChapterStatus(chapterStatusId);
        return ApiResponse.<String>builder()
                .result(String.format("Chapter status %s đã xoá thành công", chapterStatusId))
                .build();
    }

    @PutMapping("/{chapterStatusId}")
    ApiResponse<ChapterStatusResponse> updateChapterStatus(@PathVariable String chapterStatusId, @Valid @RequestBody ChapterStatusCreationRequest request){
        return ApiResponse.<ChapterStatusResponse>builder()
                .result(chapterStatusService.updateChapterStatus(chapterStatusId, request))
                .build();
    }

}
