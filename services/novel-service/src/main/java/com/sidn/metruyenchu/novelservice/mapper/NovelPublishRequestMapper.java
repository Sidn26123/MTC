package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.publish.NovelPublishRequestCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.publish.NovelPublishRequestResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NovelAuthorMapper.class})
public interface NovelPublishRequestMapper {

    NovelPublishRequest toEntity(NovelPublishRequestCreationRequest request);

    @Mapping(target = "statusLabel", expression = "java(entity.getStatus() != null ? entity.getStatus().getLabel() : null)")
    @Mapping(target = "novelId", source = "novel.id")
    NovelPublishRequestResponse toResponse(NovelPublishRequest entity);

    List<NovelPublishRequestResponse> toResponses(List<NovelPublishRequest> entities);
}