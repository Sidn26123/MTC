package com.sidn.metruyenchu.fileservice.mapper;

import com.sidn.metruyenchu.fileservice.dto.FileInfo;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementCreateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.StoredFile.FileManagementResponse;
import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@EnableJpaAuditing
public interface FileManagementMapper {

    FileManagement toFileManagement(FileManagementCreateRequest request);
    FileManagement toFileManagement(FileInfo fileInfo);
    @Mapping(source = "isDeleted", target = "isDeleted")
    FileManagementResponse toFileManagementResponse(FileManagement fileManagement);

    List<FileManagementResponse> toFileManagementResponses(List<FileManagement> fileManagements);

    void updateFileManagement(@MappingTarget FileManagement fileManagement, FileManagementUpdateRequest request);
}
