package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.DeleteRatingRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.RatingResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.RatingMapper;
import com.sidn.metruyenchu.feedbackservice.repository.RatingRepository;
import com.sidn.metruyenchu.feedbackservice.repository.httpclient.NovelClient;
import com.sidn.metruyenchu.feedbackservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetChapterInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetNovelInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromToken;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RatingService {
    RatingRepository ratingRepository;

    RatingMapper ratingMapper;

    NovelClient novelClient;


    public List<RatingResponse> getAllReviews() {
        return ratingRepository.findAll()
                .stream()
                .map(ratingMapper::toResponse)
                .toList();
    }

    public RatingResponse getReview(String reviewId) {
        return ratingMapper.toResponse(
                ratingRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"))
        );
    }

    public Rating getRating(String ratingId) {
        return (
                ratingRepository.findById(ratingId).orElseThrow(() -> new RuntimeException("Review not found"))
        );
    }



    public RatingResponse createReview(RatingCreationRequest request){
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setRatedBy(userId);
        //Kiểm tra novel và chapter có tồn tại không
        //Kiểm tra chapter có thuộc novel không
        if (request.getNovelId() == null || request.getLastReadChapterId() == null){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }

        NovelResponse novelResponse = callFeignGetNovelInfo(novelClient, request.getNovelId()).getResult();
        ChapterResponse chapterResponse = callFeignGetChapterInfo(novelClient, request.getLastReadChapterId()).getResult();

        if (novelResponse == null || chapterResponse == null){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }

        if (!Objects.equals(chapterResponse.getNovel(), request.getNovelId())){
            throw new AppException(ErrorCode.CHAPTER_NOT_BELONG_TO_NOVEL);
        }

//        if (chapterResponse.getChapterIdx() < novelResponse.getChapterReadToComment())

        var rating = ratingMapper.toEntity(request);

        try{
            rating = ratingRepository.save(rating);
            log.info("Rating created with id: {}", rating.getId());
            novelClient.ratingNovel(
                    request.getNovelId(),
                    RatingRequest.builder()
                            .rate(rating.getRate())
                            .isNew(true)
                            .novelId(request.getNovelId())
                            .build()
            );

        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.RATING_ALREADY_EXISTS);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ratingMapper.toResponse(rating);
    }

    @Transactional
    public void deleteReview(String reviewId) {
        Rating rating = ratingRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.RATING_NOT_FOUND));

        ratingRepository.softDelete(reviewId);

        novelClient.deleteRatingNovel(
                rating.getNovelId(),
                DeleteRatingRequest.builder()
                        .ratingId(reviewId)
                        .rate(rating.getRate())
                        .novelId(rating.getNovelId())
                        .build()
        );
    }

    @Transactional
    public RatingResponse updateReview(String reviewId, RatingUpdateRequest request){

        Rating existingRating = ratingRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));

        ratingMapper.updateRating(existingRating, request);
        existingRating = ratingRepository.save(existingRating);
        return ratingMapper.toResponse(existingRating);
    }

    public PageResponse<RatingResponse> getRatingInNovel(String novelId, BaseFilterRequest request) {
        if (novelId == null){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }

        Pageable pageable = PageUtils.from(request);

        Page<Rating> ratingPage = ratingRepository.findALlByNovelId(novelId, pageable);


        return PageUtils.toPageResponse(
                ratingPage,
                ratingMapper::toResponse,
                request.getPage()
        );
    }

    public Rating getRatingById(String id){
        return ratingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.RATING_NOT_FOUND)
        );
    }



    public void decrementTotalLikes(String ratingId){
        ratingRepository.decrementTotalLikes(ratingId);
    }
//
    public void incrementTotalLikes(String ratingId){
        ratingRepository.incrementTotalLikes(ratingId);
    }

    public void decrementTotalDisLikes(String ratingId){
        ratingRepository.decrementTotalDisLikes(ratingId);
    }

    public void incrementTotalDisLikes(String ratingId){
        ratingRepository.incrementTotalDisLikes(ratingId);
    }
}
