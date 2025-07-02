package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.like.*;
import com.sidn.metruyenchu.feedbackservice.dto.response.LikeResponse;
import com.sidn.metruyenchu.feedbackservice.service.LikeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromContext;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromToken;

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
    ApiResponse<PageResponse<LikeResponse>> getAllLikes(@ModelAttribute BaseFilterRequest request) {
        return ApiResponse.<PageResponse<LikeResponse>>builder()
                .result(likeService.getLikes(request))
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

    @DeleteMapping("/comment/{commentId}")
    ApiResponse<Void> unlikeComment(@PathVariable("commentId") String commentId, @RequestBody UnLikeCommentRequest request) {
        request.setCommentId(commentId);
        String userId = getUserIdFromContext();
        return ApiResponse.<Void>builder()
                .result(likeService.unlikeComment(commentId, userId))
                .build();
    }

    @DeleteMapping("/rating/{ratingId}")
    ApiResponse<Void> unlikeRating(@PathVariable("ratingId") String ratingId, @RequestBody UnLikeRatingRequest request) {
        request.setRatingId(ratingId);
        String userId = getUserIdFromContext();

        return ApiResponse.<Void>builder()
                .result(likeService.unlikeRating(ratingId, userId))
                .build();
    }

//    @PostMapping("/rating/{ratingId}/toggle")
//    ApiResponse<LikeResponse> toggleLikeInRating(@PathVariable("ratingId") String ratingId, @RequestBody ToggleLikeRequest request) {
//        request.setParentId(ratingId);
//        return ApiResponse.<LikeResponse>builder()
//                .result(likeService.toggleLikeInRating(request))
//                .build();
//    }

}
