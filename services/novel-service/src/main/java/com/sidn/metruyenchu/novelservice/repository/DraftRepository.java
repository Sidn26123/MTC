package com.sidn.metruyenchu.novelservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.sidn.metruyenchu.novelservice.entity.Draft;

import java.util.List;
import java.util.Optional;

@Repository
public interface DraftRepository extends JpaRepository<Draft, String>, JpaSpecificationExecutor<Draft> {
    List<Draft> findByPublisherAndIsDeletedFalseOrderByUpdatedAtDesc(String publisher);
    Optional<Draft> findByIdAndIsDeletedFalse(String id);
    Page<Draft> findByPublisherAndIsDeletedFalse(String publisher, Pageable pageable);
}
