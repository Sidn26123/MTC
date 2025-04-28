package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelStatusCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.NovelStatusMapper;
import com.sidn.metruyenchu.novelservice.repository.NovelStatusRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelStatusService {
    NovelStatusRepository novelStatusRepository;
    NovelStatusMapper novelStatusMapper;

    public NovelStatusResponse createNovelStatus(NovelStatusCreationRequest request) {
        NovelStatus novelStatus = novelStatusMapper.toNovelStatus(request);
        novelStatusRepository.findByName(novelStatus.getName())
                .ifPresent(checkNovelStatus -> {
                    throw new AppException(ErrorCode.NOVEL_STATUS_ALREADY_EXISTED);
                });

        try {
            novelStatus = novelStatusRepository.save(novelStatus);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return novelStatusMapper.toNovelStatusResponse(novelStatus);
    }
    public List<NovelStatusResponse> getAllNovelStatus() {
        return novelStatusRepository.findAll().stream()
                .map(novelStatusMapper::toNovelStatusResponse)
                .toList();
    }
    public NovelStatusResponse getNovelStatusById(String novelStatusId) {
        return novelStatusRepository.findById(novelStatusId)
                .map(novelStatusMapper::toNovelStatusResponse)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_STATUS_NOT_FOUND));
    }

    public NovelStatusResponse getNovelStatusByName(String name){
        if (novelStatusRepository.findByName(name).isEmpty()) {
            throw new AppException(ErrorCode.NOVEL_STATUS_NOT_FOUND);
        }
        return novelStatusMapper.toNovelStatusResponse(novelStatusRepository.findByName(name).get());
    }


    public NovelStatusResponse updateNovelStatus(String novelStatusId, NovelStatusCreationRequest request) {
        NovelStatus novelStatus = novelStatusRepository.findById(novelStatusId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_STATUS_NOT_FOUND));
        novelStatus.setName(request.getName());
        novelStatusRepository.findByName(novelStatus.getName())
                .ifPresent(checkNovelStatus -> {
                    throw new AppException(ErrorCode.NOVEL_STATUS_ALREADY_EXISTED);
                });

        try {
            novelStatus = novelStatusRepository.save(novelStatus);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return novelStatusMapper.toNovelStatusResponse(novelStatus);
    }

    public void deleteNovelStatus(String novelStatusId) {
        NovelStatus novelStatus = novelStatusRepository.findById(novelStatusId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_STATUS_NOT_FOUND));
        novelStatus.setIsDeleted(true);
        try{
            novelStatusRepository.save(novelStatus);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public List<NovelStatusResponse> getActivatingList() {
        return novelStatusRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(novelStatusMapper::toNovelStatusResponse)
                .toList();
    }

}