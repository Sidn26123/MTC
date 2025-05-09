package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.*;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterContentResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterListResponse;
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
    ApiResponse<PageResponse<ChapterResponse>> getAllChapters(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        return ApiResponse.<PageResponse<ChapterResponse>>builder()
                .result(chapterService.getAllChapters(page, size))
                .build();
        //        log.info("abc");
//        return ApiResponse.<List<ChapterResponse>>builder()
//                .result(chapterService.getAllChapters())
//                .build();
    }

    @PostMapping("/getChapterById")
    ApiResponse<ChapterResponse> getChapterById(@RequestBody String chapterId){
        log.info("log");
        log.info("chapterId: {}", chapterId);
        return ApiResponse.<ChapterResponse>builder()
                .result(chapterService.getChapter(chapterId))
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
    ApiResponse<ChapterResponse> updateChapter(@PathVariable String chapterId, @Valid @RequestBody ChapterUpdateRequest request){
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

    @GetMapping("/novel/{novelId}")
    ApiResponse<PageResponse<ChapterResponse>> getChaptersByNovelId(@PathVariable String novelId,
                                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size){
        ChaptersOfNovelGetRequest request = ChaptersOfNovelGetRequest.builder()
                .novelId(novelId)
                .build();
        request.setPage(page);
        request.setSize(size);
        return ApiResponse.<PageResponse<ChapterResponse>>builder()
                .result(chapterService.getChaptersByNovelId(request))
                .build();
    }


    @GetMapping("/novel/{novelId}/chapter/{chapterId}/content")
    ApiResponse<String> getChapterContent(@PathVariable String novelId, @PathVariable String chapterId, @RequestBody ChapterContentGetRequest request){
        request.setChapterId(chapterId);

        return ApiResponse.<String>builder()
                .result(chapterService.getRawChapterContent(request))
                .build();
    }

    @GetMapping("/truyen/{novelSlug}/chuong-{chapterIdx}")
    ApiResponse<ChapterContentResponse> getChapterContent(@PathVariable String novelSlug, @PathVariable Integer chapterIdx){
        ChapterContentGetRequest request = ChapterContentGetRequest.builder().build();
        request.setNovelSlug(novelSlug);
        request.setChapterIdx(chapterIdx);

        return ApiResponse.<ChapterContentResponse>builder()
                .result(chapterService.getChapterContent(request))
                .build();
    }

    @GetMapping("/truyen/{novelSlug}/chapterList")
    ApiResponse<PageResponse<ChapterListResponse>> getChapterList(@PathVariable String novelSlug, @RequestBody ChapterListGetRequest request){
//        ChapterListGetRequest request = ChapterListGetRequest.builder().build();
        request.setNovelSlug(novelSlug);

        return ApiResponse.<PageResponse<ChapterListResponse>>builder()
                .result(chapterService.getChapterList(request))
                .build();
    }



    @GetMapping("/novel")
    ApiResponse<PageResponse<ChapterResponse>> getNovels(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<String> sects,
            @RequestParam(required = false) List<String> worldScenes,
            @RequestParam(required = false) List<String> mainCharacterTraits
    )
        {


            return null;
        }
}
