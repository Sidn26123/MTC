package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    Chapter toChapter(ChapterCreationRequest request);
    ChapterResponse toChapterResponse(Chapter chapter);
    List<ChapterResponse> toChapterResponses(List<Chapter> chapters);
}
