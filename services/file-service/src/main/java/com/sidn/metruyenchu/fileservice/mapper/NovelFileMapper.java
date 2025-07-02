package com.sidn.metruyenchu.fileservice.mapper;

import com.sidn.metruyenchu.fileservice.dto.request.ChapterContentUploadRequest;
import com.sidn.metruyenchu.fileservice.dto.request.NovelFileUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.NovelFileResponse;
import com.sidn.metruyenchu.fileservice.entity.NovelFileManagement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface NovelFileMapper {
    NovelFileManagement toNovelFile(ChapterContentUploadRequest request);

    NovelFileResponse toNovelFileResponse(NovelFileManagement novelFileManagement);

    List<NovelFileResponse> toNovelFileResponses(List<NovelFileManagement> novelFileManagements);

    void updateNovelFile(@MappingTarget NovelFileManagement novelFileManagement, NovelFileUpdateRequest request);

}
