package com.sidn.metruyenchu.fileservice.controller;

import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.response.CloudinaryResponse;
import com.sidn.metruyenchu.fileservice.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CloudinaryController {
    CloudinaryService cloudinaryService;


    @PostMapping("/upload")
    public ApiResponse<CloudinaryResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Upload file: {}", file.getOriginalFilename());
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File upload không được để trống!");
        }

        CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, file.getOriginalFilename());

        return ApiResponse.<CloudinaryResponse>builder()
                .result(cloudinaryResponse)
                .build();
    }

    @PostMapping("/prepare-folder/{folder}")
    public ApiResponse<String> prepareFolder(@RequestParam("folder") String folder) {
        log.info("Prepare folder: {}", folder);
        cloudinaryService.prepareStorageForNovel(folder);
        return ApiResponse.<String>builder()
                .result("Folder prepared successfully")
                .build();
    }


}
