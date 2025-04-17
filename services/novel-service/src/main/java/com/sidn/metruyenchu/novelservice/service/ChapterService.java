package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterContentGetRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.ChapterCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterContentResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterPublishCheckResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatusDetail;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.ChapterMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatusRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.httpclient.FileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService {
    ChapterRepository chapterRepository;
    NovelRepository novelRepository;

    ChapterMapper  chapterMapper;
    ChapterStatusRepository chapterStatusRepository;

    FileClient fileClient;

    public ChapterResponse getChapter(String chapterId) {
        log.info("chapterId: {}", chapterId);
        return chapterMapper.toChapterResponse(
                chapterRepository.findById(chapterId).orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND))
        );
    }

    public PageResponse<ChapterResponse> getAllChapters(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        var pageData = chapterRepository.findAll(pageable);

        return PageResponse.<ChapterResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(chapterMapper::toChapterResponse).toList())
                .build();
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

        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        chapter.setNovel(novel);

        List<ChapterStatus> chapterStatuses = new ArrayList<>();
        chapterStatuses = chapterStatusRepository.findAllById(request.getChapterStatus());

        //create chapter status details
        List<ChapterStatusDetail> chapterStatusDetails = new ArrayList<>();
        for (ChapterStatus chapterStatus : chapterStatuses) {
            ChapterStatusDetail chapterStatusDetail = ChapterStatusDetail.builder()
                    .chapter(chapter)
                    .chapterStatus(chapterStatus)
                    .build();
            chapterStatusDetails.add(chapterStatusDetail);
        }

        List<ChapterStatusResponse> chapterStatusResponses = new ArrayList<>();
        for (ChapterStatusDetail chapterStatusDetail : chapterStatusDetails) {
            ChapterStatusResponse chapterStatusResponse = ChapterStatusResponse.builder()
                    .id(chapterStatusDetail.getChapterStatus().getId())
                    .name(chapterStatusDetail.getChapterStatus().getName())
                    .build();
            chapterStatusResponses.add(chapterStatusResponse);
        }

        chapter.setChapterStatus(chapterStatusDetails);

        try {
            chapter = chapterRepository.save(chapter);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.CHAPTER_ALREADY_EXISTS);
        }
        ChapterResponse chapterResponse = chapterMapper.toChapterResponse(
                chapter);
        chapterResponse.setChapterStatus(chapterStatusResponses);

        return chapterResponse;
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

    public PageResponse<ChapterResponse> getChaptersByNovelId(String novelId, int page, int size){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
//        return chapterRepository.findByNovel(novel)
//                .stream()
//                .map(chapterMapper::toChapterResponse)
//                .toList();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = chapterRepository.findByNovelId(novelId, pageable);


        return PageResponse.<ChapterResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(chapterMapper::toChapterResponse).toList())
                .build();
    }

    private List<ChapterStatusDetail> createManyRelation(Chapter chapter, List<ChapterStatus> chapterStatuses) {
        List<ChapterStatusDetail> chapterStatusDetails = new ArrayList<>();
        for (ChapterStatus chapterStatus : chapterStatuses) {
            ChapterStatusDetail chapterStatusDetail = ChapterStatusDetail.builder()
                    .chapter(chapter)
                    .chapterStatus(chapterStatus)
                    .build();
            chapterStatusDetails.add(chapterStatusDetail);
        }
        return chapterStatusDetails;
    }

    private ChapterResponse fillManyRelationResponse(ChapterResponse response, List<String> chapterStatusIds) {
        List<ChapterStatusResponse> chapterStatusResponses = new ArrayList<>();
        for (String chapterStatusId : chapterStatusIds) {
            ChapterStatusResponse chapterStatusResponse = ChapterStatusResponse.builder()
                    .id(chapterStatusId)
                    .build();
            chapterStatusResponses.add(chapterStatusResponse);
        }

        response.setChapterStatus(chapterStatusResponses);

        return response;
    }
    private List<ChapterStatus> getChapterStatus (List<ChapterStatusDetail> chapterStatusDetails) {
        List<ChapterStatus> chapterStatuses = new ArrayList<>();
        for (ChapterStatusDetail chapterStatusDetail : chapterStatusDetails) {
            chapterStatuses.add(chapterStatusDetail.getChapterStatus());
        }

        return chapterStatuses;
    }

    public String getRawChapterContent(ChapterContentGetRequest request) {
        String content = fileClient.getChapterContent(request.getChapterId());


        return content;
    }

    public ChapterContentResponse getChapterContent(ChapterContentGetRequest request) {
        Chapter chapter = chapterRepository.findByNovelSlugAndChapterIdxAndIsDeletedIsFalse(
                request.getNovelSlug(),
                request.getChapterIdx()
        ).orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));


        return chapterMapper.toChapterContentResponse(chapter);
    }

    public ChapterPublishCheckResponse checkChapterSuitForPublish(String chapterId) {
        Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        if (chapter.getContent().length() < 100) {
            return ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Nội dung chương phải lớn hơn 100 ký tự")
                    .suitable(false)
                    .build();
        }else if (chapter.getName().length() < 10) {
            return ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Tiêu đề chương phải lớn hơn 10 ký tự")
                    .suitable(false)
                    .build();
        }
        return ChapterPublishCheckResponse.builder()
                .chapterId(chapter.getId())
                .suitable(true)
                .build();
    }
    public List<ChapterPublishCheckResponse> checkChaptersSuitForPublishById(List<String> chapterIds) {
        List<ChapterPublishCheckResponse> responses = new ArrayList<>();
        for (String chapterId : chapterIds) {
            Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(chapterId)
                    .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
            checkContentChapterConstraits(responses, chapter);
        }
        return responses;
    }

    public List<ChapterPublishCheckResponse> checkChaptersSuitForPublish(List<Chapter> chapters){
        List<ChapterPublishCheckResponse> responses = new ArrayList<>();
        for (Chapter chapter : chapters) {
            checkContentChapterConstraits(responses, chapter);
        }
        return responses;
    }

    private void checkContentChapterConstraits(List<ChapterPublishCheckResponse> responses, Chapter chapter) {
        if (chapter.getContent().length() < 10) {
            responses.add(ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Nội dung chương phải lớn hơn 10 ký tự")
                    .suitable(false)
                    .build());
        }else if (chapter.getName().length() < 10) {
            responses.add(ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Tiêu đề chương phải lớn hơn 10 ký tự")
                    .suitable(false)
                    .build());
        }
    }

    public int getTotalChaptersByNovelId(String novelId) {
        return chapterRepository.countByNovelId(novelId);
    }
}
