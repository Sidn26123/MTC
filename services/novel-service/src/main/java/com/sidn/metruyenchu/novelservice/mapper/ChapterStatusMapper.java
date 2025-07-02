package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChapterStatusMapper {
    ChapterStatus toChapterStatus(ChapterStatusCreationRequest request);

    @Mapping(source = "id", target = "id")
    ChapterStatusResponse toChapterStatusResponse(ChapterStatus chapterStatus);

    List<ChapterStatusResponse> toChapterStatusResponses(List<ChapterStatus> chapterStatuses);
}
