package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.RatingResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface RatingMapper {
    Rating toEntity(RatingCreationRequest request);

    @Mapping(target = "id", source = "id")
    RatingResponse toResponse(Rating rating);

    List<RatingResponse> toRatingResponses(List<Rating> ratings);

    void updateRating(@MappingTarget Rating rating, RatingUpdateRequest request);
}
