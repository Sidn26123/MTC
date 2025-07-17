package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sidn.metruyenchu.novelservice.entity.NovelMainCharacterTrait;

import java.util.List;

@Repository
public interface CharacterTraitStatisticsRepository extends JpaRepository<NovelMainCharacterTrait, String> {
    
    @Query("SELECT new com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto(ct.mainCharacterTrait.id, ct.mainCharacterTrait.name, COUNT(ct), 'CHARACTER_TRAIT') " +
           "FROM NovelMainCharacterTrait ct WHERE ct.novel.isDeleted = false " +
           "GROUP BY ct.mainCharacterTrait.id, ct.mainCharacterTrait.name " +
           "ORDER BY COUNT(ct) DESC")
    List<NovelClassificationDto> getNovelsByCharacterTrait();
}