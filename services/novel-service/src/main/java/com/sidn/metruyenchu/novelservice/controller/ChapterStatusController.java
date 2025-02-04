package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.ChapterStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
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

    @PostMapping
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
        log.info("abc");
        ApiResponse<ChapterStatusResponse> chapterStatusResponse = new ApiResponse<>();
        return ApiResponse.<List<ChapterStatusResponse>>builder()
                .result(chapterStatusService.getAllChapterStatus())
                .build();
    }
}
