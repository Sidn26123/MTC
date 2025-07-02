package com.sidn.metruyenchu.novelservice.mapper;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelf.BookShelfUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelf.BookShelfResponse;
import com.sidn.metruyenchu.novelservice.entity.BookShelf;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookShelfMapper {
    BookShelf toEntity(BookShelfCreateRequest request);

    BookShelfResponse toResponse(BookShelf bookShelf);

    List<BookShelfResponse> toBookShelfResponses(List<BookShelf> bookShelves);

    void updateEntity(@MappingTarget BookShelf bookShelf, BookShelfUpdateRequest request);
}
