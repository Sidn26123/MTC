package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.chatbot.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {
    List<DocumentChunk> findByDocumentId(Long documentId);
}