package com.sidn.metruyenchu.fileservice.mapper;

import com.sidn.metruyenchu.fileservice.dto.request.Chapter.ChapterContentCreationRequest;
import com.sidn.metruyenchu.fileservice.dto.response.Chapter.ChapterContentResponse;
import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChapterContentMapper {
    ChapterContent toChapterContent(ChapterContentCreationRequest request);

    ChapterContentResponse toChapterContentResponse(ChapterContent chapterContent);

    List<ChapterContentResponse> toChapterContentResponse(List<ChapterContent> chapterContents);
}
