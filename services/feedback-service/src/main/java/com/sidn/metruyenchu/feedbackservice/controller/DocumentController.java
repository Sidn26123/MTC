package com.sidn.metruyenchu.feedbackservice.controller;
import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentChunkRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.*;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentChunkResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentResponse;
import com.sidn.metruyenchu.feedbackservice.service.CommentService;
import com.sidn.metruyenchu.feedbackservice.service.DocumentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DocumentController {

    DocumentService documentService;

    @PostMapping
    ApiResponse<DocumentResponse> createDocument(@Valid @RequestBody DocumentRequest request) {
        ApiResponse<DocumentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(documentService.createDocument(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<DocumentResponse>> getAllDocuments() {
        ApiResponse<List<DocumentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(documentService.getAllDocuments());
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<DocumentResponse> getDocument(@PathVariable Long id) {
        ApiResponse<DocumentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(documentService.getDocumentById(id));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<DocumentResponse> updateDocument(@PathVariable Long id, @Valid @RequestBody DocumentRequest request) {
        ApiResponse<DocumentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(documentService.updateDocument(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ApiResponse<>();
    }

    @GetMapping("/{id}/chunks")
    ApiResponse<List<DocumentChunkResponse>> getChunks(@PathVariable Long id) {
        ApiResponse<List<DocumentChunkResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(documentService.getChunksByDocumentId(id));
        return apiResponse;
    }

    @PostMapping("/{id}/chunks")
    ApiResponse<DocumentChunkResponse> addChunk(@PathVariable Long id, @Valid @RequestBody DocumentChunkRequest request) {
        request.setDocumentId(id);
        ApiResponse<DocumentChunkResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(documentService.addChunk(request));
        return apiResponse;
    }
}
