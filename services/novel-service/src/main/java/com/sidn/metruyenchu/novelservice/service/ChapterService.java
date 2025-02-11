package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.ChapterMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService {
    ChapterRepository chapterRepository;

    ChapterMapper  chapterMapper;

    public ChapterResponse getChapter(String chapterId) {
        return chapterMapper.toChapterResponse(
                chapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    public List<ChapterResponse> getAllChapters() {
        return chapterRepository.findAll()
                .stream()
                .map(chapterMapper::toChapterResponse)
                .toList();
    }
//
//    public List<ChapterResponse> getChaptersByNovelId(String novelId) {
//        return chapterRepository.findByNovelId(novelId)
//                .stream()
//                .map(chapterMapper::toChapterResponse)
//                .toList();
//    }

    public ChapterResponse createChapter(ChapterCreationRequest request){
        var chapter = chapterMapper.toChapter(request);

        try {
            chapter = chapterRepository.save(chapter);
            log.info(chapter.getId());
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.CHAPTER_ALREADY_EXISTS);
        }
        ChapterResponse chapterResponse = chapterMapper.toChapterResponse(
                chapter);
        log.info(chapterResponse.getId());
        return chapterMapper.toChapterResponse(
                chapter);
    }

    public void deleteChapter(String chapterId) {
        if (!chapterRepository.existsById(chapterId)) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        chapterRepository.deleteById(chapterId);
    }

    public ChapterResponse updateChapter(String chapterId, ChapterCreationRequest request) {
        var chapter = chapterRepository.findById(chapterId).
                orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        chapter = chapterMapper.toChapter(request);

        try {
            chapter = chapterRepository.save(chapter);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.CHAPTER_ALREADY_EXISTS);
        }

        return chapterMapper.toChapterResponse(chapter);
    }



}
