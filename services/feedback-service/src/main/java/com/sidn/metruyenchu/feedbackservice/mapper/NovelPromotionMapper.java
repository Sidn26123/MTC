package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion.NovelPromotionCreateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion.NovelPromotionUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.novelPromotion.NovelPromotionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.NovelPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface NovelPromotionMapper {

    NovelPromotion toEntity(NovelPromotionCreateRequest request);

    @Mapping(target = "id", source = "id")
    NovelPromotionResponse toResponse(NovelPromotion novelPromotion);

    List<NovelPromotionResponse> toResponses(List<NovelPromotion> novelPromotions);

    void updateNovelPromotion(@MappingTarget NovelPromotion novelPromotion, NovelPromotionUpdateRequest request);
}