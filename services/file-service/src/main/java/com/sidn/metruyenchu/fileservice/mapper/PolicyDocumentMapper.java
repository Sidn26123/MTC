package com.sidn.metruyenchu.fileservice.mapper;

import com.sidn.metruyenchu.fileservice.dto.request.policy.PolicyDocumentCreateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.policy.PolicyDocumentUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.policy.PolicyDocumentResponse;
import com.sidn.metruyenchu.fileservice.entity.PolicyDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PolicyDocumentMapper {
    PolicyDocument toEntity(PolicyDocumentCreateRequest request);

    PolicyDocumentResponse toResponse(PolicyDocument entity);

    void updateEntity(@MappingTarget PolicyDocument entity, PolicyDocumentUpdateRequest request);
}
