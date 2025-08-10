package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentChunkRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentChunkResponse;
import com.sidn.metruyenchu.feedbackservice.entity.chatbot.DocumentChunk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentChunkMapper {

    DocumentChunk toDocumentChunk(DocumentChunkRequest request);

    DocumentChunkResponse toDocumentChunkResponse(DocumentChunk chunk);

    List<DocumentChunkResponse> toDocumentChunkResponses(List<DocumentChunk> chunks);

    void updateDocumentChunk(@MappingTarget DocumentChunk chunk, DocumentChunkRequest request);
}