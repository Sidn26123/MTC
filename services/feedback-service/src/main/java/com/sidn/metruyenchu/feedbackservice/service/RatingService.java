package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.rating.RatingCreationRequest;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
                .map(ratingMapper::toRatingResponse)
                .toList();
    }

    public RatingResponse getReview(String reviewId) {
        return ratingMapper.toRatingResponse(
                ratingRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"))
        );
    }



    public RatingResponse createReview(RatingCreationRequest request){
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setRatedBy(userId);
        //Kiểm tra novel và chapter có tồn tại không
        //Kiểm tra chapter có thuộc novel không
        if (request.getRatingInNovelId() == null || request.getLastReadChapterId() == null){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }

        NovelResponse novelResponse = callFeignGetNovelInfo(novelClient, request.getRatingInNovelId()).getResult();
        ChapterResponse chapterResponse = callFeignGetChapterInfo(novelClient, request.getLastReadChapterId()).getResult();

        if (novelResponse == null || chapterResponse == null){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }

        if (!Objects.equals(chapterResponse.getNovel(), request.getRatingInNovelId())){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        var rating = ratingMapper.toRating(request);



        return ratingMapper.toRatingResponse(rating);
    }

    public void deleteReview(String reviewId) {
        if (!ratingRepository.existsById(reviewId)){
            throw new RuntimeException("Review not found");
        }
        ratingRepository.deleteById(reviewId);
    }

    public RatingResponse updateReview(String reviewId, RatingUpdateRequest review){
        Rating existingRating = ratingRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
        ratingMapper.updateRating(existingRating, review);
        return ratingMapper.toRatingResponse(ratingRepository.save(existingRating));
    }

    public Rating getRatingById(String id){
        return ratingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.RATING_NOT_FOUND)
        );
    }
}
