package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.enums.ChapterStatGenericList;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterStatService {
    ChapterRepository chapterRepository;
    @Transactional
    public Void increaseStat(String novelId, ChapterStatGenericList type) {
        switch (type) {
            case TOTAL_COMMENT -> chapterRepository.incrementTotalComments(novelId);
            case VIEW_COUNT    -> chapterRepository.incrementViewCount(novelId);
        }

        return null;
    }

    @Transactional
    public Void decreaseStat(String novelId, ChapterStatGenericList type) {
        switch (type) {
            case TOTAL_COMMENT -> chapterRepository.decrementTotalComments(novelId);
            case VIEW_COUNT    -> chapterRepository.decrementViewCount(novelId);
        }
        return null;
    }

    @Transactional
    public Void changeStat(String novelId, ChapterStatGenericList type, int amount) {
        switch (type) {
            case AMOUNT_TO_UNLOCK -> chapterRepository.updateAmountToUnlock(novelId, amount);
        }

        return null;
    }
}
