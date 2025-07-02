package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NovelStatusMapper {
    NovelStatus toNovelStatus(NovelStatusCreationRequest novelStatus);
    @Mapping(source = "id", target = "id")
    NovelStatusResponse toNovelStatusResponse(NovelStatus novelStatus);
    List<NovelStatusResponse> toNovelStatusResponses(List<NovelStatus> novelStatuses);
}
