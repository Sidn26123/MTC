package com.sidn.metruyenchu.fileservice.controller;

import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.PageResponse;
import com.sidn.metruyenchu.fileservice.dto.request.*;
import com.sidn.metruyenchu.fileservice.dto.response.FileResponse;
import com.sidn.metruyenchu.fileservice.dto.response.NovelFileResponse;
import com.sidn.metruyenchu.fileservice.enums.FilePurposeEnum;
import com.sidn.metruyenchu.fileservice.service.FileStorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileController {
    FileStorageService fileStorageService;

    @PostMapping("/prepare-folder")
    public ApiResponse<FileResponse> prepareFolder(@RequestBody PrepareStorageRequest request) {


        return ApiResponse.<FileResponse>builder()
                .result(fileStorageService.prepareStorageForNovel(request))
                .build();
    }

    @PostMapping("/upload")
    public ApiResponse<NovelFileResponse> uploadFile(@ModelAttribute ChapterUploadContentRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.<NovelFileResponse>builder()
                .result(fileStorageService.uploadContent(request, file))
                .build();
    }

    @GetMapping("/{novelId}/retrieve-chapter/{chapterId}")
    public ApiResponse<FileResponse> retrieveFile(@PathVariable("novelId") String novelId, @PathVariable("chapterId") String chapterId) {
        return ApiResponse.<FileResponse>builder()
                .result(fileStorageService.getContentFileChapterOfNovel(
                        ChapterContentGetRequest.builder()
                                .novelId(novelId)
                                .chapterId(chapterId)
                                .build()
                ))
                .build();
    }

    @DeleteMapping("/novel/{novelId}/chapter/{chapterId}")
    public ApiResponse<String> deleteFile(@RequestParam("novelId") String novelId, @RequestParam("chapterId") String chapterId) throws IOException {
        return ApiResponse.<String>builder()
                .result(fileStorageService.deleteChapterContent(
                        ChapterContentGetRequest.builder()
                                .novelId(novelId)
                                .chapterId(chapterId)
                                .build()
                ))
                .build();
    }

    @PutMapping("/novel/{novelId}/chapter/{chapterId}")
    public ApiResponse<NovelFileResponse> updateFile(@RequestParam("novelId") String novelId, @RequestParam("chapterId") String chapterId,
                                                   @ModelAttribute NovelFileUpdateRequest request  ) throws IOException {
//        request.setChapterId(chapterId);
//        request.setNovelId(novelId);

        return ApiResponse.<NovelFileResponse>builder()
                .result(fileStorageService.updateNovelFile(
                        chapterId,
                        request
                ))
                .build();
    }

    @PostMapping("/novel/{novelId}/chapter/{chapterId}/upload/image")
    public ApiResponse<NovelFileResponse> uploadImage(@PathVariable("novelId") String novelId, @PathVariable("chapterId") String chapterId,
                                                      @RequestParam("file") MultipartFile file) throws IOException {

        ChapterUploadContentRequest request = ChapterUploadContentRequest.builder()
                .novelId(novelId)
                .chapterId(chapterId)
                .build();

        return ApiResponse.<NovelFileResponse>builder()
                .result(fileStorageService.uploadImage(
                        request,
                        file
                ))
                .build();
    }

    @GetMapping("/novel/{novelId}/chapters")
    public ApiResponse<PageResponse<NovelFileResponse>> getChapters(@PathVariable("novelId") String novelId,
                                                                    @RequestParam("page") int page,
                                                                    @RequestParam("size") int size) {

        NovelFileDetailsGetRequest request = NovelFileDetailsGetRequest.builder()
                .novelId(novelId)
                .page(page)
                .size(size)
                .build();

        return ApiResponse.<PageResponse<NovelFileResponse>>builder()

                .result(fileStorageService.getChaptersOfNovel(request))
                .build();
    }

//    @GetMapping("/novel/{novelId}/chapter/{chapterId}/path")
//    public ApiResponse<FileResponse> getChapterPath(
//            @PathVariable("novelId") String novelId,
//            @PathVariable("chapterId") String chapterId
//    ) {
//        FilePathGetRequest request = FilePathGetRequest.builder()
//                .novelId(novelId)
//                .chapterId(chapterId)
//                .type(FilePurposeEnum.CHAPTER_CONTENT)
//                .build();
//
//        return ApiResponse.<FileResponse>builder()
//                .result(fileStorageService.getPath(
//                        request
//                ))
//                .build();
//    }

}
