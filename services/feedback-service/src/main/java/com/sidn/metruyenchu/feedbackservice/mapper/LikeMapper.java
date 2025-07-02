package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.like.LikeCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.like.LikeUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.LikeResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface LikeMapper {
    Like toLike(LikeCreationRequest request);

    LikeResponse toLikeResponse(Like like);

    List<LikeResponse> toLikeResponses(List<Like> likes);

    void toLikeResponse(@MappingTarget Like like, LikeUpdateRequest request);

    void updateLike(@MappingTarget Like like, LikeUpdateRequest request);

    void update(@MappingTarget Like like, LikeUpdateRequest request);
}
