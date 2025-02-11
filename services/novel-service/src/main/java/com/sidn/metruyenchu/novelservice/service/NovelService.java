package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.NovelResponse;
import com.sidn.metruyenchu.novelservice.dto.response.NovelStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.entity.NovelStatus;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.NovelMapper;
import com.sidn.metruyenchu.novelservice.mapper.NovelStatusMapper;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelStatusRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelService {
    NovelRepository novelRepository;
    NovelStatusRepository novelStatusRepository;

    NovelMapper novelMapper;
    NovelStatusMapper novelStatusMapper;
    public NovelResponse getNovel(String novelName) {
        return fetchDataMissOfNovel(novelMapper.toNovelResponse(novelRepository.findByName(novelName)));
    }

    public NovelResponse getNovelBySlug(String novelSlug) {

        return fetchDataMissOfNovel(novelMapper.toNovelResponse(novelRepository.findBySlug(novelSlug)));
    }

    public NovelResponse createNovel(NovelCreationRequest request) {
        Novel novel = novelMapper.toNovel(request);

        HashSet<NovelStatus> novelStatuses = new HashSet<>();
        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);
        novel.setStatus(novelStatuses);

        try{
            novel = novelRepository.save(novel);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
//        NovelResponse novelResponse = novelMapper.toNovelResponse(novel);
//        List<NovelStatusResponse> novelStatusesResponse = novel.getStatus().stream()
//                .map(novelStatusMapper::toNovelStatusResponse)
//                .toList();
//        novelResponse.setStatus(novelStatusesResponse);
        return fetchDataMissOfNovel(novel);
    }

    public List<NovelResponse> getAllNovel() {
        return novelRepository.findAll().stream()
                .map(this::fetchDataMissOfNovel)
                .toList();
    }

    public NovelResponse getNovelById(String novelId) {
        return fetchDataMissOfNovel(novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND)));
    }

    public NovelResponse updateNovel(String novelId, NovelUpdateRequest request){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        novelMapper.updateNovel(novel, request);

        HashSet<NovelStatus> novelStatuses = new HashSet<>();
        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);
        novel.setStatus(novelStatuses);

        try {
            novel = novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return fetchDataMissOfNovel(novel);


    }

    public void deleteNovel(String novelId) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        novel.setIsDeleted(true);
        try {
            novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public List<NovelResponse> searchNovel(String keyword) {
        return novelRepository.findByNameContaining(keyword).stream()
                .map(this::fetchDataMissOfNovel)
                .toList();
    }


    public List<NovelResponse> getNovelByAuthorId(String authorId) {
        return novelRepository.findByAuthorId(authorId).stream()
                .map(this::fetchDataMissOfNovel)
                .toList();
    }



    public NovelResponse fetchDataMissOfNovel(Novel novel){
        NovelResponse novelResponse = novelMapper.toNovelResponse(novel);

        return fetchDataMissOfNovel(novelResponse);
    }

    public NovelResponse fetchDataMissOfNovel(NovelResponse novelResponse){
        Novel novel = novelRepository.findById(novelResponse.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        List<NovelStatusResponse> novelStatusesResponse = novel.getStatus().stream()
                .map(novelStatusMapper::toNovelStatusResponse)
                .toList();
        novelResponse.setStatus(novelStatusesResponse);

        return novelResponse;
    }
}
