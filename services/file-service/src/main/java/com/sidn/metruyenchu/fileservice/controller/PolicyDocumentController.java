package com.sidn.metruyenchu.fileservice.controller;

import com.sidn.metruyenchu.fileservice.dto.ApiResponse;
import com.sidn.metruyenchu.fileservice.dto.request.policy.PolicyDocumentCreateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.policy.PolicyDocumentUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.CloudinaryResponse;
import com.sidn.metruyenchu.fileservice.dto.response.policy.PolicyDocumentResponse;
import com.sidn.metruyenchu.fileservice.service.CloudinaryService;
import com.sidn.metruyenchu.fileservice.service.PolicyDocumentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PolicyDocumentController {

    PolicyDocumentService service;

    @GetMapping("/{id}")
    public ApiResponse<PolicyDocumentResponse> getPublicById(@PathVariable String id) {
        return ApiResponse.<PolicyDocumentResponse>builder()
                .result(service.getPublicById(id))
                .build();
    }

    @GetMapping("/{slug}")
    public ApiResponse<PolicyDocumentResponse> getPublicBySlug(@PathVariable String slug) {
        return ApiResponse.<PolicyDocumentResponse>builder()
                .result(service.getPublicBySlug(slug))
                .build();
    }

    @GetMapping("/admin")
    public ApiResponse<List<PolicyDocumentResponse>> getAll() {
        return ApiResponse.<List<PolicyDocumentResponse>>builder()
                .result(service.getAll())
                .build();
    }

    @PostMapping("/admin")
    public ApiResponse<PolicyDocumentResponse> create(
            @RequestBody PolicyDocumentCreateRequest request
    ) {
        return ApiResponse.<PolicyDocumentResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/admin/{slug}")
    public ApiResponse<PolicyDocumentResponse> update(
            @PathVariable String slug,
            @Valid @RequestBody PolicyDocumentUpdateRequest request
    ) {
        return ApiResponse.<PolicyDocumentResponse>builder()
                .result(service.update(slug, request))
                .build();
    }
}
