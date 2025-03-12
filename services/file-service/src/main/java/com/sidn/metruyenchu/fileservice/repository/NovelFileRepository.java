package com.sidn.metruyenchu.fileservice.repository;

import com.sidn.metruyenchu.fileservice.dto.request.NovelFileUpdateRequest;
import com.sidn.metruyenchu.fileservice.entity.NovelFile;
import com.sidn.metruyenchu.fileservice.enums.FilePurposeEnum;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelFileRepository extends JpaRepository<NovelFile, String> {
    void deleteNovelFileByChapterId(String chapterId);
    Optional<NovelFile> findByNovelIdAndChapterIdAndIsDeletedIsFalse(String novelId, String chapterId);
    Optional<NovelFile> findByChapterId(String chapterId);
    Optional<NovelFile> findByNovelIdAndPurposeAndIsDeletedIsFalse(String novelId, FilePurposeEnum purpose);
    Page<NovelFile> findAllByNovelIdAndIsDeletedIsFalse(String novelId, Pageable pageable);
}
