package com.sidn.metruyenchu.feedbackservice.service;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentChunkRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.chatbot.DocumentRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.*;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentChunkResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.chatbot.DocumentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.chatbot.Document;
import com.sidn.metruyenchu.feedbackservice.entity.chatbot.DocumentChunk;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sidn.metruyenchu.feedbackservice.repository.DocumentChunkRepository;
import com.sidn.metruyenchu.feedbackservice.repository.DocumentRepository;
import com.sidn.metruyenchu.feedbackservice.mapper.DocumentMapper;
import com.sidn.metruyenchu.feedbackservice.mapper.DocumentChunkMapper;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DocumentService {

    DocumentRepository documentRepository;
    DocumentMapper documentMapper;
    DocumentChunkRepository chunkRepository;
    DocumentChunkMapper chunkMapper;

    public DocumentResponse createDocument(DocumentRequest request) {
        Document document = documentMapper.toDocument(request);
        return documentMapper.toDocumentResponse(documentRepository.save(document));
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentMapper.toDocumentResponses(documentRepository.findAll());
    }

    public DocumentResponse getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return documentMapper.toDocumentResponse(document);
    }

    public DocumentResponse updateDocument(Long id, DocumentRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        documentMapper.updateDocument(document, request);
        return documentMapper.toDocumentResponse(documentRepository.save(document));
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    public List<DocumentChunkResponse> getChunksByDocumentId(Long documentId) {
        return chunkMapper.toDocumentChunkResponses(chunkRepository.findByDocumentId(documentId));
    }

    public DocumentChunkResponse addChunk(DocumentChunkRequest request) {
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));
        DocumentChunk chunk = chunkMapper.toDocumentChunk(request);
        chunk.setDocument(document);
        return chunkMapper.toDocumentChunkResponse(chunkRepository.save(chunk));
    }
}
