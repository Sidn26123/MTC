package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.NovelResponse;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {NovelAuthorMapper.class})
public interface NovelMapper {

//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "novelCoverImage", target = "novelCoverImage")
    @Mapping(ignore = true, target = "status")
//    @Mapping(source = "authorId", target = "author")
    Novel toNovel(NovelCreationRequest request);

    @Mapping(ignore = true, target = "status")
    Novel toNovel(Object request);

    @Mapping(ignore = true, target = "status")
    NovelResponse toNovelResponse(Novel novel);

    @Mapping(ignore = true, target = "status")
    NovelResponse toNovelResponse(Object novel);

    List<NovelResponse> toNovelResponses(List<Novel> novels);

    @Mapping(target = "status", ignore = true)
    void updateNovel(@MappingTarget Novel novel, NovelUpdateRequest request);
}
