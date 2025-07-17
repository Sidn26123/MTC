package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.entity.NovelGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreStatisticsRepository extends JpaRepository<NovelGenre, String> {
    
    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto(g.genre.id, g.genre.name, COUNT(g), 'GENRE') " +
           "FROM NovelGenre g WHERE g.novel.isDeleted = false " +
           "GROUP BY g.genre.id, g.genre.name " +
           "ORDER BY COUNT(g) DESC")
    List<NovelClassificationDto> getNovelsByGenre();
}