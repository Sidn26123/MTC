package com.sidn.metruyenchu.fileservice.repository;

import com.sidn.metruyenchu.fileservice.entity.NovelFileManagement;
import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NovelFileManagementRepository extends JpaRepository<NovelFileManagement, String> {
    void deleteNovelFileByChapterId(String chapterId);
    Optional<NovelFileManagement> findByNovelIdAndChapterIdAndIsDeletedIsFalse(String novelId, String chapterId);
    Optional<NovelFileManagement> findByChapterId(String chapterId);
//    Optional<NovelFile> findByNovelIdAndPurposeAndIsDeletedIsFalse(String novelId, FileCategoryEnum purpose);
    Page<NovelFileManagement> findAllByNovelIdAndIsDeletedIsFalse(String novelId, Pageable pageable);

    List<NovelFileManagement> findAllByNovelIdAndCategoryAndIsDeletedIsFalse(String novelId, FileCategoryEnum fileCategoryEnum);
}
