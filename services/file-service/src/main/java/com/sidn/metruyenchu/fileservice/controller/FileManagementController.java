package com.sidn.metruyenchu.fileservice.controller;

import com.sidn.metruyenchu.fileservice.dto.PageResponse;
import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementCreateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.GetAllFileManagementRequest;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.PageRequest;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.StoredFile.FileManagementResponse;
import com.sidn.metruyenchu.fileservice.service.FileManagementService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/s-files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileManagementController {
//    FileStorageService fileStorageService;
    FileManagementService fileManagementService;


    //Lay danh sach file
    @GetMapping("/list")
    ApiResponse<PageResponse<FileManagementResponse>> getFiles(@Valid @RequestBody GetAllFileManagementRequest request, @RequestParam("page") int page, @RequestParam("size") int size) {

        request.setPageRequest(new PageRequest(page, size));

        return ApiResponse.<PageResponse<FileManagementResponse>>builder()
                .result(fileManagementService.getStoredFiles(request))
                .build();
    }
    //Lay file theo id
    @GetMapping("/{id}")
    ApiResponse<FileManagementResponse> getFile(@PathVariable("id") String id) {
        return ApiResponse.<FileManagementResponse>builder()
                .result(fileManagementService.getStoredFile(id))
                .build();
    }
    //Xoa file theo id
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteFile(@PathVariable("id") String id) {
        return ApiResponse.<Void>builder()
                .result(fileManagementService.softDeleteFile(id))
                .build();
    }
    //Create file stored record

    @PostMapping("/")
    ApiResponse<FileManagementResponse> createFile(@Valid @RequestBody FileManagementCreateRequest request) {
        return ApiResponse.<FileManagementResponse>builder()
                .result(fileManagementService.saveFileAndRes(request))
                .build();
    }
    //Download file

    //Update file
    @PutMapping("/{id}")
    ApiResponse<FileManagementResponse> updateFile(@PathVariable("id") String id, @Valid @RequestBody FileManagementUpdateRequest request) {
        return ApiResponse.<FileManagementResponse>builder()
                .result(fileManagementService.updateFile(id, request))
                .build();
    }
    //Tim kiem file


}
