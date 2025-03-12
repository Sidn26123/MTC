package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    @Mapping(target = "chapterStatus", ignore = true)
    Chapter toChapter(ChapterCreationRequest request);

    @Mapping(source = "novel.id", target = "novel")
    ChapterResponse toChapterResponse(Chapter chapter);

    List<ChapterResponse> toChapterResponses(List<Chapter> chapters);
}
