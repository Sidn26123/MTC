package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.ChapterStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterStatusMapper {
    ChapterStatus toChapterStatus(ChapterStatusCreationRequest request);

    @Mapping(source = "id", target = "id")
    ChapterStatusResponse toChapterStatusResponse(ChapterStatus chapterStatus);

    List<ChapterStatusResponse> toChapterStatusResponses(List<ChapterStatus> chapterStatuses);
}
