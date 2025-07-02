package com.sidn.metruyenchu.fileservice.controller;

import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.response.Chapter.ChapterContentResponse;
import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import com.sidn.metruyenchu.fileservice.mapper.ChapterContentMapper;
import com.sidn.metruyenchu.fileservice.service.ChapterContentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chapter-content")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterContentController {
    ChapterContentService chapterContentService;
    ChapterContentMapper chapterContentMapper;

    @GetMapping("/chapter-content/{chapterId}")
    ApiResponse<List<ChapterContentResponse>> getChapterContent(String chapterId) {
        List<ChapterContent> chapterContent = chapterContentService.getChapterContent(chapterId);
        return ApiResponse.<List<ChapterContentResponse>>builder()
                .result(chapterContentMapper.toChapterContentResponse(chapterContent))
                .build();
    }
}
