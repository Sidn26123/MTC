package com.sidn.metruyenchu.novelservice.mapper;

import com.sidn.metruyenchu.novelservice.dto.request.genre.GenreCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import com.sidn.metruyenchu.novelservice.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface GenreMapper {
    Genre toGenre(GenreCreationRequest request);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "createdAt", target = "createdAt")
//    @Mapping(source = "updatedAt", target = "updatedAt")
//    @Mapping(source = "isDeleted", target = "isDeleted")
//    @Mapping(source = "isActive", target = "isActive")
    GenreResponse toGenreResponse(Genre genre);
    List<GenreResponse> toGenreResponses(List<Genre> genres);
}
