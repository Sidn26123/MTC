package com.sidn.metruyenchu.fileservice.repository;
import com.sidn.metruyenchu.fileservice.entity.NovelFileManagement;
import com.sidn.metruyenchu.fileservice.entity.PolicyDocument;
import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyDocumentRepository extends JpaRepository<PolicyDocument, String> {
    Optional<PolicyDocument> findBySlugAndIsPublicTrue(String slug);
    Optional<PolicyDocument> findBySlug(String slug);
}
