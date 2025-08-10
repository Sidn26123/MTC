package com.sidn.metruyenchu.feedbackservice.mapper;

import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentChunkRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentChunkResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.chatbot.Document;
import com.sidn.metruyenchu.feedbackservice.entity.chatbot.DocumentChunk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentMapper {

    Document toDocument(DocumentRequest request);

    DocumentResponse toDocumentResponse(Document document);

    List<DocumentResponse> toDocumentResponses(List<Document> documents);

    void updateDocument(@MappingTarget Document document, DocumentRequest request);
}