package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelAuthorCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelAuthorResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NovelAuthorMapper {
    NovelAuthor toNovelAuthor(NovelAuthorCreationRequest request);

    NovelAuthorResponse toNovelAuthorResponse(NovelAuthor novelAuthor);

    List<NovelAuthorResponse> toNovelAuthorResponses(List<NovelAuthor> novelAuthors);

}
