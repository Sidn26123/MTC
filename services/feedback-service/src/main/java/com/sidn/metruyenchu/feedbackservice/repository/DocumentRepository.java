package com.sidn.metruyenchu.feedbackservice.repository;

import com.sidn.metruyenchu.feedbackservice.entity.chatbot.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}