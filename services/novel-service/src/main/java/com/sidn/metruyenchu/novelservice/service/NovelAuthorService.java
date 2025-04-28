package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelAuthorCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelAuthorResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.NovelAuthorMapper;
import com.sidn.metruyenchu.novelservice.repository.NovelAuthorRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelAuthorService {
    NovelAuthorRepository novelAuthorRepository;

    NovelAuthorMapper novelAuthorMapper;

    public NovelAuthorResponse createNovelAuthor(@Valid @RequestBody NovelAuthorCreationRequest request) {
        NovelAuthor novelAuthor = novelAuthorMapper.toNovelAuthor(request);
        novelAuthorRepository.findByName(novelAuthor.getName())
                .ifPresent(checkNovelAuthor -> {
                    throw new AppException(ErrorCode.NOVEL_ALREADY_EXISTS);
                });

        try {
            novelAuthor = novelAuthorRepository.save(novelAuthor);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return novelAuthorMapper.toNovelAuthorResponse(novelAuthor);
    }

    public List<NovelAuthorResponse> getAllNovelAuthor() {
        return novelAuthorRepository.findAll().stream()
                .map(novelAuthorMapper::toNovelAuthorResponse)
                .collect(Collectors.toList());
    }

    public NovelAuthorResponse getNovelAuthorById(String novelAuthorId) {
        return novelAuthorRepository.findById(novelAuthorId)
                .map(novelAuthorMapper::toNovelAuthorResponse)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

    }

    public NovelAuthorResponse updateNovelAuthor(String novelAuthorId, NovelAuthorCreationRequest request) {
        NovelAuthor novelAuthor = novelAuthorRepository.findById(novelAuthorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        novelAuthor.setName(request.getName());
        novelAuthorRepository.findByName(novelAuthor.getName())
                .ifPresent(checkNovelAuthor -> {
                    throw new AppException(ErrorCode.NOVEL_ALREADY_EXISTS);
                });

        try {
            novelAuthor = novelAuthorRepository.save(novelAuthor);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return novelAuthorMapper.toNovelAuthorResponse(novelAuthor);
    }

    public void deleteNovelAuthor(String novelAuthorId) {
        NovelAuthor novelAuthor = novelAuthorRepository.findById(novelAuthorId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

    }

    public List<NovelAuthorResponse> getNovelAuthorByName(String name) {
        return novelAuthorRepository.findByName(name).stream()
                .map(novelAuthorMapper::toNovelAuthorResponse)
                .collect(Collectors.toList());
    }

    public List<NovelAuthorResponse> getActivatingList() {
        return novelAuthorRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(novelAuthorMapper::toNovelAuthorResponse)
                .collect(Collectors.toList());
    }
}
