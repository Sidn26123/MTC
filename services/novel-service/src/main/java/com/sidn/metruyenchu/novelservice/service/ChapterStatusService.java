package com.sidn.metruyenchu.novelservice.service;


import com.sidn.metruyenchu.novelservice.dto.request.ChapterStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.ChapterStatusMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatusRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<ChapterStatusResponse> getAllChapterStatus() {
        return chapterStatusRepository.findAll()
                .stream()
                .map(chapterStatusMapper::toChapterStatusResponse)
                .toList();
    }



}
