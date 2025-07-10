package com.sidn.metruyenchu.fileservice.service;

import com.sidn.metruyenchu.fileservice.dto.request.policy.PolicyDocumentCreateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.policy.PolicyDocumentUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.policy.PolicyDocumentResponse;
import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import com.sidn.metruyenchu.fileservice.entity.PolicyDocument;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.fileservice.mapper.PolicyDocumentMapper;
import com.sidn.metruyenchu.fileservice.repository.ChapterContentRepository;
import com.sidn.metruyenchu.fileservice.repository.PolicyDocumentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sidn.metruyenchu.fileservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.shared_library.utils.TokenUtils.getUserIdFromToken;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PolicyDocumentService {

    PolicyDocumentRepository repository;
    PolicyDocumentMapper mapper;

    public PolicyDocumentResponse create(PolicyDocumentCreateRequest request){
        String adminUser = getUserIdFromToken(getTokenFromContext());
        PolicyDocument document = mapper.toEntity(request);
        document.setVersion(1);

        return mapper.toResponse(repository.save(document));
    }

    public PolicyDocumentResponse getPublicBySlug(String slug) {
        return mapper.toResponse(
                repository.findBySlugAndIsPublicTrue(slug)
                    .orElseThrow(() -> new AppException(ErrorCode.POLICY_NOT_FOUND))
        );
    }

    public List<PolicyDocumentResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public PolicyDocumentResponse update(String slug, PolicyDocumentUpdateRequest request) {
        PolicyDocument document = repository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.POLICY_NOT_FOUND));
        String adminUser = getUserIdFromToken(getTokenFromContext());
        mapper.updateEntity(document, request);
        document.setVersion(document.getVersion() + 1);
        document.setUpdatedBy(adminUser);

        return mapper.toResponse(repository.save(document));
    }

    public PolicyDocumentResponse getPublicById(String id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.POLICY_NOT_FOUND))
        );
    }

    public Void delete(String id){
        PolicyDocument document = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POLICY_NOT_FOUND));
        document.setDeleted(true);
        return null;
    }
}
