package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.repository.CharacterTraitStatisticsRepository;
import com.sidn.metruyenchu.novelservice.repository.GenreStatisticsRepository;
import com.sidn.metruyenchu.novelservice.repository.SectStatisticsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class ClassificationStatisticService {

    GenreStatisticsRepository genreRepository;
    CharacterTraitStatisticsRepository traitRepository;
    SectStatisticsRepository sectRepository;

    public List<NovelClassificationDto> getNovelsByGenre() {
        return genreRepository.getNovelsByGenre();
    }

    public List<NovelClassificationDto> getNovelsByCharacterTrait() {
        return traitRepository.getNovelsByCharacterTrait();
    }

    public List<NovelClassificationDto> getNovelsBySect() {
        return sectRepository.getNovelsBySect();
    }
}
