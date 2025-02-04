package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.NovelResponse;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NovelMapper {
    Novel toNovel(NovelCreationRequest request);

    NovelResponse toNovelResponse(Novel novel);

    List<NovelResponse> toNovelResponses(List<Novel> novels);


}
