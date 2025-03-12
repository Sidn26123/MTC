package com.sidn.metruyenchu.novelservice.controller;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.novel.CheckNovelExistedRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelFilterRequest;
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
    ApiResponse<PageResponse<NovelResponse>> getAllNovels(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getAllNovels(page, size))
                .build();
    }

    @GetMapping("/novel")
    ApiResponse<PageResponse<NovelResponse>> getNovelByCategory(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "sort", defaultValue = "ASC") String sort,
//            @RequestParam(required = false) String searchText,
//            @RequestParam(required = false) List<String> status,
//            @RequestParam(required = false) List<String> genres,
//            @RequestParam(required = false) List<String> sects,
//            @RequestParam(required = false) List<String> worldScenes,
//            @RequestParam(required = false) List<String> mainCharacterTraits
            @ModelAttribute NovelFilterRequest request
            ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovels(request))
                .build();
    }

    @GetMapping("/{novelSlug}")
    ApiResponse<NovelResponse> getNovel(@PathVariable String novelSlug) {
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.getNovelBySlug(novelSlug))
                .build();
    }

    @PostMapping("/getNovelById")
    ApiResponse<NovelResponse> getNovelById(@RequestBody CheckNovelExistedRequest request) {
        NovelResponse novelResponse;

        if (request.getId() != null){
            novelResponse = novelService.getNovelById(request.getId());
        } else if (request.getSlug() != null){
            novelResponse = novelService.getNovelBySlug(request.getSlug());
        }
        else{
            throw new RuntimeException("Id or slug must be provided");
        }
        return ApiResponse.<NovelResponse>builder()
                .result(novelResponse)
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

    @GetMapping("/all")
    ApiResponse<Integer> getAllNovel(@RequestParam boolean containDeleted) {
        return ApiResponse.<Integer>builder()
                .result(novelService.getAllNovel(containDeleted))
                .build();
    }

    @GetMapping("/get-novel-with-filter")
    ApiResponse<PageResponse<NovelResponse>> getNovelWithFilter(
            @ModelAttribute NovelFilterRequest request
    ) {
        return ApiResponse.<PageResponse<NovelResponse>>builder()
                .result(novelService.getNovelWithFilter(request))
                .build();
    }

    @PostMapping("/{novelId}/commented-novel")
    ApiResponse<NovelResponse> updateCommentTotal(
           @PathVariable String novelId, @ModelAttribute NovelUpdateRequest request
    ){
        return ApiResponse.<NovelResponse>builder()
                .result(novelService.updateNovel(novelId, request))
                .build();
    }

}
