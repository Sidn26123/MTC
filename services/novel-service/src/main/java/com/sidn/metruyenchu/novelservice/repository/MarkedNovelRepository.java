package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.MarkedNovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkedNovelRepository extends JpaRepository<MarkedNovel, String> {
    Page<MarkedNovel> findAllByUserId(String userId, Pageable pageable);

    Optional<MarkedNovel> findByUserIdAndNovelId(String userId, String novelId);
}
