package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelfItem.BookShelfItemResponse;
import com.sidn.metruyenchu.novelservice.entity.BookShelfItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookShelfItemMapper {
    BookShelfItem toEntity(BookShelfItemCreateRequest request);

    BookShelfItemResponse toResponse(BookShelfItem bookShelfItem);

    List<BookShelfItemResponse> toResponses(List<BookShelfItem> bookShelfItems);

    void updateEntity(@MappingTarget BookShelfItem bookShelfItem, BookShelfItemUpdateRequest request);
    void updateEntity(@MappingTarget BookShelfItem bookShelfItem, BookShelfItemCreateRequest request);

}
