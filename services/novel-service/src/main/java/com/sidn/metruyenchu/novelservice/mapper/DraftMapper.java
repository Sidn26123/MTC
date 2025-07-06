package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.draft.DraftCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelfItem.BookShelfItemResponse;
import com.sidn.metruyenchu.novelservice.dto.response.draft.DraftResponse;
import com.sidn.metruyenchu.novelservice.entity.BookShelfItem;
import com.sidn.metruyenchu.novelservice.entity.Draft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DraftMapper {
    @Mapping(target = "novel", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    Draft toEntity(DraftCreationRequest request);

    @Mapping(source = "novel.id", target = "novel")
    @Mapping(source = "chapter.id", target = "chapter")
    @Mapping(source = "state", target = "state")
    DraftResponse toResponse(Draft draft);
}
