package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import com.sidn.metruyenchu.novelservice.enums.PublishRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NovelPublishRequestRepository extends JpaRepository<NovelPublishRequest, String> {
    Optional<NovelPublishRequest> findByRequestedBy(String requestedBy);

    Page<NovelPublishRequest> findAllByStatus(PublishRequestStatus request, Pageable pageable);

    Page<NovelPublishRequest> findAllByRequestedBy(String requestedBy, Pageable pageable);
}
