package com.sidn.metruyenchu.fileservice.repository;

import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterContentRepository extends JpaRepository<ChapterContent, String> {
    Optional<ChapterContent> findByChapterId(String chapterId);

    List<ChapterContent> findAllByChapterId(String chapterId);
}
