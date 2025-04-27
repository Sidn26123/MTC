package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.publish.PublishRequestActionLogCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.publish.PublishRequestActionLogUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.publish.PublishRequestActionLogResponse;
import com.sidn.metruyenchu.novelservice.entity.PublishRequestActionLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NovelAuthorMapper.class})
public interface PublishRequestActionLogMapper {

    PublishRequestActionLog toEntity(PublishRequestActionLogCreationRequest request);

    @Mapping(target = "actionLabel", expression = "java(entity.getAction() != null ? entity.getAction().getLabel() : null)")
    PublishRequestActionLogResponse toResponse(PublishRequestActionLog entity);

    List<PublishRequestActionLogResponse> toResponses(List<PublishRequestActionLog> entities);

    void update(@MappingTarget PublishRequestActionLog entity, PublishRequestActionLogUpdateRequest request);
}