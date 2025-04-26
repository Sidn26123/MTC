package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.RatingResponse;
import com.sidn.metruyenchu.feedbackservice.service.RatingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReviewController {
    RatingService reviewService;

    @PostMapping()
    ApiResponse<RatingResponse> createReview(@Valid @RequestBody RatingCreationRequest request) {
        ApiResponse<RatingResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewService.createReview(request));
        return apiResponse;
    }

    @GetMapping()
    ApiResponse<List<RatingResponse>> getAllReviews() {
        return ApiResponse.<List<RatingResponse>>builder()
                .result(reviewService.getAllReviews())
                .build();
    }

    @GetMapping("/{reviewId}")
    ApiResponse<RatingResponse> getReview(@PathVariable("reviewId") String reviewId){
        return ApiResponse.<RatingResponse>builder()
                .result(reviewService.getReview(reviewId))
                .build();
    }

    @DeleteMapping("/{reviewId}")
    void deleteReview(@PathVariable("reviewId") String reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @PutMapping("/{reviewId}")
    RatingResponse updateReview(@PathVariable("reviewId") String reviewId, @RequestBody RatingUpdateRequest request) {
        return reviewService.updateReview(reviewId, request);
    }



}
