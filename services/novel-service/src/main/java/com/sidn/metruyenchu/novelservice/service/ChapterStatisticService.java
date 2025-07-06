package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.statistic.ChapterViewDistributionDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.NovelClassificationDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopChapterDto;
import com.sidn.metruyenchu.novelservice.dto.request.statistic.TopNovelDto;
import com.sidn.metruyenchu.novelservice.enums.TimeSegmentUnit;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatisticsRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelStatisticsRepository;
import com.sidn.metruyenchu.shared_library.dto.request.TimeRangeStatisticDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class ChapterStatisticService {

    ChapterStatisticsRepository chapterRepository;

    public List<TopChapterDto> getTopChaptersByViews(int limit) {
        List<Object[]> results = chapterRepository.getTopChaptersByViews(PageRequest.of(0, limit));
        return results.stream().map(row -> {
            String chapterId = (String) row[0];
            String chapterName = (String) row[1];
            String novelId = (String) row[2];
            String novelName = (String) row[3];
            Long bookmarkCount = ((Number) row[4]).longValue();
            Long viewCount = ((Number) row[5]).longValue();
            Integer rank = ((Number) row[6]).intValue();

            return TopChapterDto.builder()
                    .chapterId(chapterId)
                    .chapterName(chapterName)
                    .novelId(novelId)
                    .novelName(novelName)
                    .bookmarkCount(bookmarkCount)
                    .viewCount(viewCount)
                    .rank(rank)
                    .build();
        }).collect(Collectors.toList());
        //        return chapterRepository.getTopChaptersByViews(PageRequest.of(0, limit));
    }

    public List<ChapterViewDistributionDto> getChapterViewDistribution() {
        return chapterRepository.getChapterViewDistribution();
    }

    public Long getTotalChapterCommentsBetween(LocalDateTime start, LocalDateTime end) {
        return chapterRepository.getTotalChapterCommentsBetween(start, end);
    }
}
