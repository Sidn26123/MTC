package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterContentResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterListResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChapterMapper {
    @Mapping(target = "chapterStatus", ignore = true)
    Chapter toChapter(ChapterCreationRequest request);

    @Mapping(source = "novel.id", target = "novel")
    @Mapping(target = "stateLabel", expression = "java(chapter.getState() != null ? chapter.getState().getLabel() : null)")
    ChapterResponse toChapterResponse(Chapter chapter);

    List<ChapterResponse> toChapterResponses(List<Chapter> chapters);

    ChapterContentResponse toChapterContentResponse(Chapter chapter);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "chapterIdx", target = "chapterIdx")
    ChapterListResponse toChapterListResponse(Chapter chapter);

    @Mapping(target = "chapterStatus", ignore = true)
    void update(@MappingTarget Chapter chapter, ChapterUpdateRequest request);
}
