package com.sidn.metruyenchu.novelservice.service;


import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.ChapterStatusMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatusRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterStatusService {
    ChapterStatusRepository chapterStatusRepository;

    ChapterStatusMapper chapterStatusMapper;

    public ChapterStatusResponse createChapterStatus(ChapterStatusCreationRequest request){
        var chapterStatus = chapterStatusMapper.toChapterStatus(request);

        try {
            chapterStatus = chapterStatusRepository.save(chapterStatus);
            log.info(chapterStatus.getId());
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.CHAPTER_STATUS_NAME_EXISTED);
        }
        ChapterStatusResponse chapterStatusResponse = chapterStatusMapper.toChapterStatusResponse(
                chapterStatus);
        log.info(chapterStatusResponse.getId());
        return chapterStatusMapper.toChapterStatusResponse(
                chapterStatus);
    }

    public ChapterStatusResponse getChapterStatus(String chapterId) {
        return chapterStatusMapper.toChapterStatusResponse(
                chapterStatusRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ChapterStatusResponse> getAllChapterStatus() {
        return chapterStatusRepository.findAll()
                .stream()
                .map(chapterStatusMapper::toChapterStatusResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteChapterStatus(String chapterId) {
        if (!chapterStatusRepository.existsById(chapterId)) {
            throw new AppException(ErrorCode.CHAPTER_STATUS_NAME_NOT_EXISTED);
        }
        chapterStatusRepository.deleteById(chapterId);
    }

    public ChapterStatusResponse updateChapterStatus(String chapterId, ChapterStatusCreationRequest request) {
        var chapterStatus = chapterStatusRepository.findById(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_STATUS_NAME_NOT_EXISTED));
        chapterStatus.setName(request.getName());
        chapterStatusRepository.save(chapterStatus);
        return chapterStatusMapper.toChapterStatusResponse(chapterStatus);
    }


}
