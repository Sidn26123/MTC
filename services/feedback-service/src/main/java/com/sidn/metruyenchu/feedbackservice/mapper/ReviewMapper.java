package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReviewResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring")
@EnableJpaAuditing
public interface ReviewMapper {
    Review toReview(ReviewCreationRequest request);

    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toReviewResponses(List<Review> reviews);

    void updateReview(@MappingTarget Review review, ReviewUpdateRequest request);
}
