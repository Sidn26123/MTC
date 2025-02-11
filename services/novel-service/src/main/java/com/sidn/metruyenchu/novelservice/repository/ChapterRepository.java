package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, String> {
//    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse(c) " +
//            "FROM Chapter c WHERE c.novel.id = :novelId AND c.isDeleted = false ORDER BY c.chapterIdx ASC")
//    List<ChapterResponse> findChaptersByNovelId(@Param("novelId") String novelId);
}
