package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.NovelResponse;
import com.sidn.metruyenchu.novelservice.mapper.NovelMapper;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelService {
    NovelRepository novelRepository;

    NovelMapper novelMapper;
    public NovelResponse getNovel(String novelName) {
        return novelMapper.toNovelResponse(novelRepository.findByName(novelName));
    }

//    public NovelResponse createNovel(NovelCreationRequest request) {
//
//    }
}
