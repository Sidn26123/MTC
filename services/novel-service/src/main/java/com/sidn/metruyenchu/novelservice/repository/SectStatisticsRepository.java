package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.entity.NovelSect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectStatisticsRepository extends JpaRepository<NovelSect, String> {
    
    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto(s.sect.id, s.sect.name, COUNT(s), 'SECT') " +
           "FROM NovelSect s WHERE s.novel.isDeleted = false " +
           "GROUP BY s.sect.id, s.sect.name " +
           "ORDER BY COUNT(s) DESC")
    List<NovelClassificationDto> getNovelsBySect();
}