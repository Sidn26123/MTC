package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, String> {
    Optional<Chapter> findByNovel(Novel novel);
//    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse(c) " +
//            "FROM Chapter c WHERE c.novel.id = :novelId AND c.isDeleted = false ORDER BY c.chapterIdx ASC")
//    List<ChapterResponse> findChaptersByNovelId(@Param("novelId") String novelId);
    Page<Chapter> findByNovelId(String novelId, Pageable pageable);
}
