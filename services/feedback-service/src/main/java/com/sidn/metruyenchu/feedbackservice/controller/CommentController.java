package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.*;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.service.CommentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {
    CommentService commentService;

    @PostMapping()
    ApiResponse<CommentResponse> createComment(@Valid @RequestBody CommentCreationRequest request) {
        ApiResponse<CommentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(commentService.createComment(request));
        return apiResponse;
    }

    @GetMapping()
    ApiResponse<List<CommentResponse>> getAllComments() {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getAllComments())
                .build();
    }

    @GetMapping("/{commentId}")
    ApiResponse<CommentResponse> getComment(@PathVariable("commentId") String commentId){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.getComment(commentId))
                .build();
    }

    @DeleteMapping("/{commentId}")
    void deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping("/{commentId}")
    CommentResponse updateComment(@PathVariable("commentId") String commentId, @RequestBody CommentUpdateRequest request) {
        return commentService.updateComment(commentId, request);
    }

    @GetMapping("/chapter")
    ApiResponse<PageResponse<CommentResponse>> getCommentsInChapter(
            @ModelAttribute CommentInChapterGetRequest request
    ) {
        return ApiResponse.<PageResponse<CommentResponse>>builder()
                .result(commentService.getCommentInChapter(request))
                .build();
    }

    @GetMapping("/novel/{novelSlug}")
    ApiResponse<PageResponse<CommentResponse>> getCommentsInNovel(
            @PathVariable String novelSlug,
            @ModelAttribute CommentOfNovelGetRequest request

    ) {
//        CommentOfNovelGetRequest request = CommentOfNovelGetRequest.builder().build();
        request.setNovelSlug(novelSlug);
        return ApiResponse.<PageResponse<CommentResponse>>builder()
                .result(commentService.getCommentInNovel(request))
                .build();
    }

    @PostMapping("/filter")
    ApiResponse<PageResponse<CommentResponse>> getCommentsByFilter(
            @RequestBody CommentFilterRequest request
    ) {
        return ApiResponse.<PageResponse<CommentResponse>>builder()
                .result(commentService.filter(request))
                .build();
    }

}
