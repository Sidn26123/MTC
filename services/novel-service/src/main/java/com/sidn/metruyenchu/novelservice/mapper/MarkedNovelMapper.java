package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelRequest;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.markedNovel.MarkedNovelResponse;
import com.sidn.metruyenchu.novelservice.entity.MarkedNovel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MarkedNovelMapper {
    MarkedNovel toEntity(MarkedNovelCreateRequest request);
    MarkedNovel toEntity(MarkedNovelRequest request);

    MarkedNovelResponse toResponse(MarkedNovel markedNovel);

    List<MarkedNovelResponse> toResponses(List<MarkedNovel> listMarkedNovel);

    void updateEntity(@MappingTarget MarkedNovel markedNovel, MarkedNovelUpdateRequest request);
    void updateEntity(@MappingTarget MarkedNovel markedNovel, MarkedNovelCreateRequest request);

    void updateEntity(@MappingTarget MarkedNovel markedNovel, MarkedNovelRequest request);
}
