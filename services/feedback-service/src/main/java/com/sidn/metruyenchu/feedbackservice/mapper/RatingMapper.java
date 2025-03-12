package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.ReviewUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.ReviewResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring")
@EnableJpaAuditing
public interface ReviewMapper {
    Rating toReview(ReviewCreationRequest request);

    ReviewResponse toReviewResponse(Rating rating);

    List<ReviewResponse> toReviewResponses(List<Rating> ratings);

    void updateReview(@MappingTarget Rating rating, ReviewUpdateRequest request);
}
