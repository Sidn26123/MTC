package com.sidn.metruyenchu.fileservice.controller;

import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.request.ChapterContentUploadRequest;
import com.sidn.metruyenchu.fileservice.dto.request.PrepareStorageRequest;
import com.sidn.metruyenchu.fileservice.dto.response.Chapter.ChapterContentResponse;
import com.sidn.metruyenchu.fileservice.dto.response.FileResponse;
import com.sidn.metruyenchu.fileservice.dto.response.FolderResponse;
import com.sidn.metruyenchu.fileservice.dto.response.NovelFileResponse;
import com.sidn.metruyenchu.fileservice.dto.response.StoredFile.FileManagementResponse;
import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import com.sidn.metruyenchu.fileservice.mapper.ChapterContentMapper;
import com.sidn.metruyenchu.fileservice.service.ChapterContentService;
import com.sidn.metruyenchu.fileservice.service.utils.FileService;
import com.sidn.metruyenchu.fileservice.service.NovelFileManagementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelFileManagementController {
    NovelFileManagementService novelFileManagementService;
    FileService fileService;
    ChapterContentService chapterContentService;
    ChapterContentMapper chapterContentMapper;
    //get file upload dir from .yml
//
    @PostMapping("/prepare-folder")
    public ApiResponse<FolderResponse> prepareFolder(@RequestBody PrepareStorageRequest request) {


        return ApiResponse.<FolderResponse>builder()
                .result(novelFileManagementService.prepareStorageForNovel(request))
                .build();
    }

//    @PostMapping("/novel/{novelId}/chapter/{chapterId}")
//    public ApiResponse<NovelFileResponse> uploadChapterContent(@PathVariable("chapterId") String chapterId,
//                                                               @PathVariable("novelId") String novelId,
//                                                              @RequestParam("file") MultipartFile file) throws IOException {
//
//        ChapterContentUploadRequest request = ChapterContentUploadRequest.builder()
//                .chapterId(chapterId)
//                .novelId(novelId)
//                .file(file)
//                .build();
//        return ApiResponse.<NovelFileResponse>builder()
//                .result(novelFileManagementService.createChapterContent(
//                    request
//                ))
//                .build();
//    }

    @PostMapping("/media/upload")
    ApiResponse<FileResponse> uploadMedia(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.<FileResponse>builder()
                .result(fileService.uploadFile(file))
                .build();
    }

    @PostMapping("/novel/{novelId}/chapter/{chapterId}/content")
    public ApiResponse<NovelFileResponse> uploadChapterContent1(@PathVariable("chapterId") String chapterId,
                                                                @PathVariable("novelId") String novelId,
                                                                @RequestBody ChapterContentUploadRequest request) throws IOException {

        request.setChapterId(chapterId);
        request.setNovelId(novelId);

        return ApiResponse.<NovelFileResponse>builder()
                .result(novelFileManagementService.uploadChapterContent(
                        request
                ))
                .build();
    }

    @PutMapping("/chapter/{chapterId}/content")
    public ApiResponse<NovelFileResponse> updateChapterContent(@PathVariable("chapterId") String chapterId,
                                                               @RequestParam("file") MultipartFile file

    ) throws IOException {


        return ApiResponse.<NovelFileResponse>builder()
                .result(novelFileManagementService.updateChapterContent(
                        chapterId, file
                ))
                .build();
    }




    /**
     * Download file (return về kiểu binary)
     * @param fileId
     * @return
     * @throws IOException
     */
    @GetMapping("/media/download/{fileId}")
    ResponseEntity<Resource> downloadMedia(@PathVariable("fileId") String fileId) throws IOException {

        FileManagement fileManagement = fileService.getFile(fileId);

        Resource resource = fileService.getContentOfFileManagement(fileManagement);
        return ResponseEntity.<Resource>ok()
                .header(HttpHeaders.CONTENT_TYPE, fileManagement.getContentType())
                .body(resource);
    }

    @DeleteMapping("/media/{fileName}")
    ApiResponse<String> deleteMedia(@PathVariable("fileName") String fileName) throws IOException {
        return ApiResponse.<String>builder()
                .result(fileService.deleteFile(fileName))
                .build();
    }

    @DeleteMapping("/media/complete-delete/{fileName}")
    ApiResponse<String> completeDeleteMedia(@PathVariable("fileName") String fileName) throws IOException {
        return ApiResponse.<String>builder()
                .result(fileService.completeDeleteFile(fileName))
                .build();
    }

    @PutMapping("/media/{fileName}")
    ApiResponse<FileManagementResponse> updateMedia(@PathVariable("fileName") String fileName,
                                                    @RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.<FileManagementResponse>builder()
                .result(fileService.updateFile(fileName, file))
                .build();
    }

    @GetMapping("/chapter/{chapterId}/content")
    ApiResponse<List<ChapterContentResponse>> getChapterContent(@PathVariable("chapterId") String chapterId) {
        List<ChapterContent> chapterContent = chapterContentService.getChapterContent(chapterId);
        return ApiResponse.<List<ChapterContentResponse>>builder()
                .result(chapterContentMapper.toChapterContentResponse(chapterContent))
                .build();
    }


    @PostMapping("/novel/{novelId}/image/cover")
    public ApiResponse<NovelFileResponse> uploadNovelCover(@PathVariable("novelId") String novelId,
                                                            @RequestParam("file") MultipartFile file) throws IOException {

        return ApiResponse.<NovelFileResponse>builder()
                .result(novelFileManagementService.uploadNovelCover(
                        novelId,
                        file
                ))
                .build();
    }

//
//    @PostMapping("/upload")
//    public ApiResponse<Object> uploadFile(@ModelAttribute ChapterUploadContentRequest request, @RequestParam("file") MultipartFile file) throws IOException {
//
//
//        return ApiResponse.<Object>builder()
//                .result(fileStorageService.uploadFile(file))
//                .build();
//    }
//
//    @PutMapping("/novel/{novelId}/chapter/{chapterId}")
//
//    @PostMapping("/novel/{novelId}/chapter/{chapterId}")
//    public ApiResponse<NovelFileResponse> uploadChapterContent(@PathVariable("novelId") String novelId, @PathVariable("chapterId") String chapterId,
//                                                          @RequestParam("file") MultipartFile file) throws IOException {
//
//        ChapterUploadContentRequest request = ChapterUploadContentRequest.builder()
//                .novelId(novelId)
//                .chapterId(chapterId)
//                .build();
//
//        return ApiResponse.<NovelFileResponse>builder()
//                .result(fileStorageService.uploadChapterContent(
//                        request,
//                        file
//                ))
//                .build();
//    }
//
//    @GetMapping("/{novelId}/retrieve-chapter/{chapterId}")
//    public ApiResponse<FileResponse> retrieveFile(@PathVariable("novelId") String novelId, @PathVariable("chapterId") String chapterId) {
//        return ApiResponse.<FileResponse>builder()
//                .result(fileStorageService.getContentFileChapterOfNovel(
//                        ChapterContentGetRequest.builder()
//                                .novelId(novelId)
//                                .chapterId(chapterId)
//                                .build()
//                ))
//                .build();
//    }
//
//    @DeleteMapping("/novel/{novelId}/chapter/{chapterId}")
//    public ApiResponse<String> deleteFile(@PathVariable("novelId") String novelId, @PathVariable("chapterId") String chapterId) throws IOException {
//        NovelFileResponse novelFileResponse = fileStorageService.getNovelFileByNovelIdAndChapterId(novelId, chapterId);
//
//        return ApiResponse.<String>builder()
//                .result(fileStorageService.deleteNovelFile(FileDeleteRequest.builder()
//                        .fileId(novelFileResponse.getId())
//                        .build()
//                )).build();
//    }
//
//
//
//
//    @PutMapping("/novel/{novelId}/chapter/{chapterId}")
//    public ApiResponse<NovelFileResponse> updateFile(@RequestParam("novelId") String novelId, @RequestParam("chapterId") String chapterId,
//                                                   @ModelAttribute NovelFileUpdateRequest request  ) throws IOException {
////        request.setChapterId(chapterId);
////        request.setNovelId(novelId);
//
//        return ApiResponse.<NovelFileResponse>builder()
//                .result(fileStorageService.updateNovelFile(
//                        chapterId,
//                        request
//                ))
//                .build();
//    }
//
//    @PostMapping("/novel/{novelId}/chapter/{chapterId}/upload/image")
//    public ApiResponse<NovelFileResponse> uploadImage(@PathVariable("novelId") String novelId, @PathVariable("chapterId") String chapterId,
//                                                      @RequestParam("file") MultipartFile file) throws IOException {
//
//        ChapterUploadContentRequest request = ChapterUploadContentRequest.builder()
//                .novelId(novelId)
//                .chapterId(chapterId)
//                .build();
//
//        return ApiResponse.<NovelFileResponse>builder()
//                .result(fileStorageService.uploadImage(
//                        request,
//                        file
//                ))
//                .build();
//    }
//
//    @GetMapping("/novel/{novelId}/chapters")
//    public ApiResponse<PageResponse<NovelFileResponse>> getChapters(@PathVariable("novelId") String novelId,
//                                                                    @RequestParam("page") int page,
//                                                                    @RequestParam("size") int size) {
//
//        NovelFileDetailsGetRequest request = NovelFileDetailsGetRequest.builder()
//                .novelId(novelId)
//                .page(page)
//                .size(size)
//                .build();
//
//        return ApiResponse.<PageResponse<NovelFileResponse>>builder()
//
//                .result(fileStorageService.getChaptersOfNovel(request))
//                .build();
//    }

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
