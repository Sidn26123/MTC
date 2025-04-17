package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.publish.NovelPublishRequestCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.publish.NovelPublishRequestResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NovelAuthorMapper.class})
public interface NovelPublishRequestMapper {

    NovelPublishRequest toEntity(NovelPublishRequestCreationRequest request);

    NovelPublishRequestResponse toResponse(NovelPublishRequest entity);

    List<NovelPublishRequestResponse> toResponses(List<NovelPublishRequest> entities);
}