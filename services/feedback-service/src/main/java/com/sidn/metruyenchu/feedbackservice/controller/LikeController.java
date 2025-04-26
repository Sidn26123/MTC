package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.like.LikeCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.like.LikeUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.LikeResponse;
import com.sidn.metruyenchu.feedbackservice.service.LikeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LikeController {
    LikeService likeService;

    @PostMapping()
    ApiResponse<LikeResponse> createLike(@Valid @RequestBody LikeCreationRequest request) {
        ApiResponse<LikeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(likeService.createLike(request));
        return apiResponse;
    }

    @GetMapping()
    ApiResponse<List<LikeResponse>> getAllLikes() {
        return ApiResponse.<List<LikeResponse>>builder()
                .result(likeService.getAllLikes())
                .build();
    }

    @PostMapping("/comment/{commentId}")
    ApiResponse<LikeResponse> createLikeForComment(@PathVariable("commentId") String commentId, @Valid @RequestBody LikeCreationRequest request) {
        request.setParentId(commentId);

        return ApiResponse.<LikeResponse>builder()
                .result(likeService.likeComment(request))
                .build();
    }

    @PostMapping("/rating/{ratingId}")
    ApiResponse<LikeResponse> createLikeForRating(@PathVariable("ratingId") String ratingId, @Valid @RequestBody LikeCreationRequest request) {
        request.setParentId(ratingId);

        return ApiResponse.<LikeResponse>builder()
                .result(likeService.likeRating(request))
                .build();
    }



    @GetMapping("/{likeId}")
    ApiResponse<LikeResponse> getLike(@PathVariable("likeId") String likeId){
        return ApiResponse.<LikeResponse>builder()
                .result(likeService.getLike(likeId))
                .build();
    }

    @DeleteMapping("/{likeId}")
    void deleteLike(@PathVariable("likeId") String likeId) {
        likeService.deleteLike(likeId);
    }

    @PutMapping("/{likeId}")
    LikeResponse updateLike(@PathVariable("likeId") String likeId, @RequestBody LikeUpdateRequest request) {
        return likeService.updateLike(likeId, request);
    }


}
