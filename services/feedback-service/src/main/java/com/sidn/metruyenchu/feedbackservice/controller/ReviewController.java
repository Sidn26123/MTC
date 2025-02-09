package com.sidn.metruyenchu.feedbackservice.controller;

import com.sidn.metruyenchu.feedbackservice.dto.ApiResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReviewResponse;
import com.sidn.metruyenchu.feedbackservice.service.ReviewService;
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
    ReviewService reviewService;

    @PostMapping()
    ApiResponse<ReviewResponse> createReview(@Valid @RequestBody ReviewCreationRequest request) {
        ApiResponse<ReviewResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewService.createReview(request));
        return apiResponse;
    }

    @GetMapping()
    ApiResponse<List<ReviewResponse>> getAllReviews() {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getAllReviews())
                .build();
    }

    @GetMapping("/{reviewId}")
    ApiResponse<ReviewResponse> getReview(@PathVariable("reviewId") String reviewId){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.getReview(reviewId))
                .build();
    }

    @DeleteMapping("/{reviewId}")
    void deleteReview(@PathVariable("reviewId") String reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @PutMapping("/{reviewId}")
    ReviewResponse updateReview(@PathVariable("reviewId") String reviewId, @RequestBody ReviewUpdateRequest request) {
        return reviewService.updateReview(reviewId, request);
    }



}
