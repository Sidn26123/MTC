package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion.NovelPromotionCreateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.novelPromotion.NovelPromotionUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.novelPromotion.NovelPromotionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.NovelPromotion;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.NovelPromotionMapper;
import com.sidn.metruyenchu.feedbackservice.repository.NovelPromotionRepository;
import com.sidn.metruyenchu.feedbackservice.utils.PageUtils;
import com.sidn.metruyenchu.feedbackservice.utils.TokenUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelPromotionService {
    NovelPromotionRepository novelPromotionRepository;
    NovelPromotionMapper novelPromotionMapper;

    // Add methods for creating, updating, deleting, and retrieving novel promotions

    public PageResponse<NovelPromotionResponse> getAllNovelPromotions(BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        Page<NovelPromotion> novelPromotions = novelPromotionRepository.findAll(pageable);
        return PageUtils.toPageResponse(
                novelPromotions,
                novelPromotionMapper::toResponse,
                request.getPage()
        );
    }


    public NovelPromotionResponse createNovelPromotion(NovelPromotionCreateRequest request) {
        NovelPromotion novelPromotion = novelPromotionMapper.toEntity(request);
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            String userId = TokenUtils.getUserIdFromContext();
            request.setUserId(userId);
        }
        novelPromotion = novelPromotionRepository.save(novelPromotion);
        return novelPromotionMapper.toResponse(novelPromotion);
    }

    public NovelPromotionResponse getNovelPromotion(String id) {
        NovelPromotion novelPromotion = novelPromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNKNOWN_ERROR));
        return novelPromotionMapper.toResponse(novelPromotion);
    }

    public void deleteNovelPromotion(String id) {
        novelPromotionRepository.deleteById(id);
    }

    public NovelPromotionResponse updateNovelPromotion(String id, NovelPromotionUpdateRequest request) {
        NovelPromotion novelPromotion = novelPromotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNKNOWN_ERROR));
        novelPromotionMapper.updateNovelPromotion(novelPromotion, request);
        novelPromotion = novelPromotionRepository.save(novelPromotion);
        return novelPromotionMapper.toResponse(novelPromotion);
    }

    //Get all promotion of a novel
    public PageResponse<NovelPromotionResponse> getAllPromotionsByNovelId(String novelId, BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        Page<NovelPromotion> promotions = novelPromotionRepository.findByNovelId(novelId, pageable);
        return PageUtils.toPageResponse(
                promotions,
                novelPromotionMapper::toResponse,
                request.getPage()
        );
    }
}
