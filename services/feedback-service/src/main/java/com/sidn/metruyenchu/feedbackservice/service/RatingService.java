package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReviewResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Review;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.ReviewMapper;
import com.sidn.metruyenchu.feedbackservice.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReviewService {
    ReviewRepository reviewRepository;

    ReviewMapper reviewMapper;

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }

    public ReviewResponse getReview(String reviewId) {
        return reviewMapper.toReviewResponse(
                reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"))
        );
    }

    public ReviewResponse createReview(ReviewCreationRequest request){
        var review = reviewMapper.toReview(request);

        try{
            review = reviewRepository.save(review);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.OBJECT_ALREADY_EXISTED);
        }

        return reviewMapper.toReviewResponse(review);
    }

    public void deleteReview(String reviewId) {
        if (!reviewRepository.existsById(reviewId)){
            throw new RuntimeException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    public ReviewResponse updateReview(String reviewId, ReviewUpdateRequest review){
        Review existingReview = reviewRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
        reviewMapper.updateReview(existingReview, review);
        return reviewMapper.toReviewResponse(reviewRepository.save(existingReview));
    }
}
