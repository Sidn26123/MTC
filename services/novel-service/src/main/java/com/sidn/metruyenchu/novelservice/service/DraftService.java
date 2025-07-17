package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.draft.DraftCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.draft.DraftUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.draft.DraftResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Draft;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.mapper.DraftMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import com.sidn.metruyenchu.novelservice.repository.DraftRepository;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.shared_library.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DraftService {
    DraftRepository draftRepository;
    NovelService novelService;
    ChapterRepository chapterRepository;
    DraftMapper draftMapper;
    private final ChapterService chapterService;

    public DraftResponse getDraft(String draftId) {
        return draftMapper.toResponse(
                draftRepository.findByIdAndIsDeletedFalse(draftId)
                        .orElseThrow(() -> new AppException(ErrorCode.DRAFT_NOT_FOUND))
        );
    }

    public PageResponse<DraftResponse> getDrafts(String publisher, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        var data = draftRepository.findByPublisherAndIsDeletedFalse(publisher, pageable);
        return PageUtils.toPageResponse(data, draftMapper::toResponse, page);
    }

    @Transactional
    public DraftResponse createDraft(DraftCreationRequest request) {
        var draft = draftMapper.toEntity(request);

        if (request.getNovelId() != null) {
            Novel novel = novelService.getNovelEntityById(request.getNovelId());
            draft.setNovel(novel);
        }

        if (request.getChapterId() != null) {
            Chapter chapter = chapterRepository.findById(request.getChapterId())
                    .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
            draft.setChapter(chapter);
        }

        return draftMapper.toResponse(draftRepository.save(draft));
    }

    @Transactional
    public void softDeleteDraft(String draftId) {
        Draft draft = draftRepository.findByIdAndIsDeletedFalse(draftId)
                .orElseThrow(() -> new AppException(ErrorCode.DRAFT_NOT_FOUND));
        draft.setIsDeleted(true);
        draftRepository.save(draft);
    }

    @Transactional
    public DraftResponse updateDraft(String draftId, DraftUpdateRequest request) {
        Draft draft = draftRepository.findByIdAndIsDeletedFalse(draftId)
                .orElseThrow(() -> new AppException(ErrorCode.DRAFT_NOT_FOUND));

        draftMapper.updateEntity(draft, request);

        if (request.getNovelId() != null) {
            Novel novel = novelService.findEntityById(request.getNovelId());
            draft.setNovel(novel);
        }

        if (request.getChapterId() != null) {
            Chapter chapter = chapterService.findEntityById(request.getChapterId());
            draft.setChapter(chapter);
        }

        return draftMapper.toResponse(draftRepository.save(draft));
    }
}
