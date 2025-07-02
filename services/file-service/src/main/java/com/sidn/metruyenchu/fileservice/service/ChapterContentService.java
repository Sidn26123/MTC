package com.sidn.metruyenchu.fileservice.service;

import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import com.sidn.metruyenchu.fileservice.exception.AppException;
import com.sidn.metruyenchu.fileservice.exception.ErrorCode;
import com.sidn.metruyenchu.fileservice.repository.ChapterContentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterContentService {
    ChapterContentRepository chapterContentRepository;

    public List<ChapterContent> getChapterContent(String chapterId) {
        return chapterContentRepository.findAllByChapterId(chapterId);
    }


}
