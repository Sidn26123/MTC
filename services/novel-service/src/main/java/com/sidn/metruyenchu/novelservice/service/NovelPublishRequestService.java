package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.publish.NovelPublishRequestCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.publish.NovelPublishRequestResponse;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import com.sidn.metruyenchu.novelservice.enums.PublishRequestStatus;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.NovelPublishRequestMapper;
import com.sidn.metruyenchu.novelservice.repository.NovelPublishRequestRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.sidn.metruyenchu.novelservice.utils.TokenUtils.getUserIdFromContext;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelPublishRequestService {

    NovelPublishRequestRepository novelPublishRequestRepository;
    NovelPublishRequestMapper novelPublishRequestMapper;
    private final NovelRepository novelRepository;

    public NovelPublishRequestResponse create(NovelPublishRequestCreationRequest request) {
        NovelPublishRequest novelPublishRequest = novelPublishRequestMapper.toEntity(request);
        novelPublishRequest.setStatus(PublishRequestStatus.PENDING);

        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        String userId = getUserIdFromContext();
        novelPublishRequest.setRequestedBy(userId);
        novelPublishRequest.setNovel(novel);
        try {
            novelPublishRequest = novelPublishRequestRepository.save(novelPublishRequest);
        } catch (Exception ex) {
            log.error("Error creating publish request", ex);
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return novelPublishRequestMapper.toResponse(novelPublishRequest);
    }

    public PageResponse<NovelPublishRequestResponse> getAll(BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        var pageData = novelPublishRequestRepository.findAll(pageable);

        return PageUtils.toPageResponse(pageData, novelPublishRequestMapper::toResponse, request.getPage());
    }

    public PageResponse<NovelPublishRequestResponse> getByRequestedBy(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var pageData = novelPublishRequestRepository.findAllByRequestedBy(username, pageable);
        return PageUtils.toPageResponse(pageData, novelPublishRequestMapper::toResponse, page);
    }

    public PageResponse<NovelPublishRequestResponse> getByPublishingStatus(BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        var pageData = novelPublishRequestRepository.findAllByStatus(PublishRequestStatus.PENDING, pageable);
        return PageUtils.toPageResponse(pageData, novelPublishRequestMapper::toResponse, request.getPage());
    }

    public NovelPublishRequestResponse getById(String id) {
        return novelPublishRequestRepository.findById(id)
                .map(novelPublishRequestMapper::toResponse).orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
    }

    public NovelPublishRequest getEntityById(String id) {
        return novelPublishRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
    }
    public void deleteById(String id) {
        if (!novelPublishRequestRepository.existsById(id)) {
            throw new AppException(ErrorCode.REQUEST_NOT_FOUND);
        }
        novelPublishRequestRepository.deleteById(id);
    }
}

