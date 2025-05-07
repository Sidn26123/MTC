package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {NovelAuthorMapper.class})
public interface NovelMapper {

    @Mapping(ignore = true, target = "status")
    @Mapping(ignore = true, target = "novelCoverImage")
    Novel toNovel(NovelCreationRequest request);

    @Mapping(ignore = true, target = "status")
    Novel toNovel(Object request);

    @Mapping(target = "novelTypeLabel", expression = "java(novel.getNovelType() != null ? novel.getNovelType().getLabel() : null)")
    @Mapping(target = "novelVisibilityLabel", expression = "java(novel.getNovelVisibility() != null ? novel.getNovelVisibility().getLabel() : null)")
    @Mapping(target = "novelStateLabel", expression = "java(novel.getNovelState() != null ? novel.getNovelState().getLabel() : null)")
    @Mapping(target = "progressStatusLabel", expression = "java(novel.getProgressStatus() != null ? novel.getProgressStatus().getLabel() : null)")
    @Mapping(target = "totalViews", source = "totalViews")
    @Mapping(ignore = true, target = "status")
    NovelResponse toNovelResponse(Novel novel);

//    @Mapping(target = "novelTypeLabel", expression = "java(novel.getNovelType() != null ? novel.getNovelType().getLabel() : null)")
//    @Mapping(target = "novelVisibilityLabel", expression = "java(novel.getNovelVisibility() != null ? novel.getNovelVisibility().getLabel() : null)")
//    @Mapping(target = "novelStateLabel", expression = "java(novel.getNovelState() != null ? novel.getNovelState().getLabel() : null)")
//    @Mapping(target = "progressStatusLabel", expression = "java(novel.getProgressStatus() != null ? novel.getProgressStatus().getLabel() : null)")
    @Mapping(ignore = true, target = "status")
    NovelResponse toNovelResponse(Object novel);

    List<NovelResponse> toNovelResponses(List<Novel> novels);

    @Mapping(target = "status", ignore = true)
    void updateNovel(@MappingTarget Novel novel, NovelUpdateRequest request);
}
