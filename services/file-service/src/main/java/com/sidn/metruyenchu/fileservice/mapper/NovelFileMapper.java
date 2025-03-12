package com.sidn.metruyenchu.fileservice.mapper;

import com.sidn.metruyenchu.fileservice.dto.request.ChapterUploadContentRequest;
import com.sidn.metruyenchu.fileservice.dto.request.NovelFileUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.NovelFileResponse;
import com.sidn.metruyenchu.fileservice.entity.NovelFile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface NovelFileMapper {
    NovelFile toNovelFile(ChapterUploadContentRequest request);

    NovelFileResponse toNovelFileResponse(NovelFile novelFile);

    List<NovelFileResponse> toNovelFileResponses(List<NovelFile> novelFiles);

    void updateNovelFile(@MappingTarget NovelFile novelFile, NovelFileUpdateRequest request);

}
