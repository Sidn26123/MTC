package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.feignclient.feedbackService.CommentNovelRequest;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.enums.ChapterStatGenericList;
import com.sidn.metruyenchu.novelservice.enums.NovelStat;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelStatService {

    NovelRepository novelRepository;

    @Transactional
    public Void increaseStat(String novelId, NovelStat type) {
        switch (type) {
            case TOTAL_COMMENTS -> novelRepository.incrementTotalComments(novelId);
            case TOTAL_RATES    -> novelRepository.incrementTotalRates(novelId);
        }
        return null;
    }

    @Transactional
    public Void decreaseStat(String novelId, NovelStat type) {
        switch (type) {
            case TOTAL_COMMENTS -> novelRepository.decrementTotalComments(novelId);
            case TOTAL_RATES    -> novelRepository.decrementTotalRates(novelId);
        }
        return null;
    }

    @Transactional
    public Void changeStat(String novelId, NovelStat type, Number value) {
        switch (type) {
            case READ_TO_COMMENT -> novelRepository.updateChapterReadToComment(novelId, value.intValue());
            case READ_TO_RATE    -> novelRepository.updateChapterReadToRate(novelId, value.intValue());
            case FULL_SET_PURCHASE_DISCOUNT -> novelRepository.updateFullSetPurchaseDiscount(novelId, value.intValue());
            case WORD_COUNT      -> novelRepository.updateWordCount(novelId, value.intValue());
            case AVG_RATE        -> novelRepository.updateAvgRate(novelId, value.floatValue());
        }
        return null;
    }

    @Transactional
    public Void rateNovel(String novelId, Float rate) {
        // Update the average rate and total rates
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        Float newAvgRate = (novel.getAvgRate() * novel.getTotalRates() + rate) / (novel.getTotalRates() + 1);
        novelRepository.updateAvgRate(novelId, newAvgRate);
        novelRepository.incrementTotalRates(novelId);
        return null;
    }

    @Transactional
    public Void commentNovel(CommentNovelRequest request){
        // Update the total comments
        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        novelRepository.incrementTotalComments(request.getNovelId());
        return null;
    }

    @Transactional
    public Void deleteComment(String novelId) {
        // Update the total comments
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        novelRepository.decrementTotalComments(novelId);
        return null;
    }
}

