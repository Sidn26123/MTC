package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.like.LikeCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.like.LikeUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.LikeResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.entity.Like;
import com.sidn.metruyenchu.feedbackservice.entity.Rating;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.CommentMapper;
import com.sidn.metruyenchu.feedbackservice.mapper.LikeMapper;
import com.sidn.metruyenchu.feedbackservice.mapper.RatingMapper;
import com.sidn.metruyenchu.feedbackservice.repository.CommentRepository;
import com.sidn.metruyenchu.feedbackservice.repository.LikeRepository;
import com.sidn.metruyenchu.feedbackservice.repository.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromToken;


@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LikeService {
    LikeRepository likeRepository;
    CommentRepository commentRepository;
    RatingRepository ratingRepository;
    LikeMapper likeMapper;
    CommentService commentService;
    RatingService ratingService;
    CommentMapper commentMapper;
    RatingMapper ratingMapper;
    public List<LikeResponse> getAllLikes() {
        return likeRepository.findAll()
                .stream()
                .map(likeMapper::toLikeResponse)
                .toList();
    }

    public LikeResponse getLike(String likeId) {
        return likeMapper.toLikeResponse(
                likeRepository.findById(likeId).orElseThrow(() -> new RuntimeException("Like not found"))
        );
    }

    public Like getLikeById(String likeId){
        return likeRepository.findById(likeId).orElseThrow(
                () -> new AppException(ErrorCode.OBJECT_NOT_FOUND)
        );
    }
    public void deleteLike(String likeId) {
        if (!likeRepository.existsById(likeId)){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }
        likeRepository.deleteById(likeId);
    }

    public Integer checkUserAlreadyActiveComment(String userId, String commentId){
        Like like = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                commentId,
                userId,
                FeedbackType.COMMENT
        ).orElse(null);
        if (like == null || like.getId() == null){
            return -1;
        }
        return Integer.parseInt(String.valueOf(like.getIsLiked()));

    }

    public LikeResponse createLike(LikeCreationRequest request){
        var like = likeMapper.toLike(request);

        Object object;
        if (request.getFeedbackType() == FeedbackType.COMMENT){
            object = commentService.getComment(request.getParentId());
        }

        else if (request.getFeedbackType() == FeedbackType.RATING){
            object = ratingService.getReview(request.getParentId());
        }



        try{
            like = likeRepository.save(like);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.OBJECT_ALREADY_EXISTED);
        }

        return likeMapper.toLikeResponse(like);
    }


    @Transactional
    public LikeResponse likeComment(LikeCreationRequest request) {
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setLikedBy(userId);

        // Kiểm tra comment tồn tại
        Comment comment = commentService.getCommentById(request.getParentId());

        // Kiểm tra user đã tương tác với comment chưa
        Optional<Like> likeCheck = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                request.getParentId(),
                request.getLikedBy(),
                FeedbackType.COMMENT
        );

        if (likeCheck.isEmpty()) {
            // Tạo like mới vì chưa có tương tác trước đó
            Like like = likeMapper.toLike(request);

            like = likeRepository.save(like); // Lưu like mới vào DB

            // Cập nhật số like/dislike bằng SQL để tránh race condition
            if (Boolean.TRUE.equals(like.getIsLiked())) {
                commentRepository.incrementTotalLikes(comment.getId());
            } else {
                commentRepository.incrementTotalDisLikes(comment.getId());
            }

            return likeMapper.toLikeResponse(like);
        }

        // Lấy like đã tồn tại từ DB
        Like existingLike = likeCheck.get();

        // Nếu đã tương tác với cùng giá trị, return luôn
        if (existingLike.getIsLiked().equals(request.getIsLiked())) {
            return likeMapper.toLikeResponse(existingLike);
        }

        // Nếu thay đổi trạng thái like/dislike
        if (Boolean.TRUE.equals(request.getIsLiked())) {
            commentRepository.incrementTotalLikes(comment.getId());
            commentRepository.decrementTotalDisLikes(comment.getId());
        } else {
            commentRepository.incrementTotalDisLikes(comment.getId());
            commentRepository.decrementTotalLikes(comment.getId());
        }

        // Cập nhật trạng thái like
        existingLike.setIsLiked(request.getIsLiked());
        likeRepository.save(existingLike); // Lưu thay đổi vào DB

        return likeMapper.toLikeResponse(existingLike);
    }

    @Transactional
    public LikeResponse likeRating(LikeCreationRequest request) {
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setLikedBy(userId);

        // Kiểm tra rating tồn tại
        Rating rating = ratingService.getRatingById(request.getParentId());

        // Kiểm tra user đã tương tác với rating chưa
        Optional<Like> likeCheck = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                request.getParentId(),
                request.getLikedBy(),
                FeedbackType.RATING
        );

        if (likeCheck.isEmpty()) {
            // Tạo like mới vì chưa có tương tác trước đó
            Like like = likeMapper.toLike(request);
            like = likeRepository.save(like); // Lưu like mới vào DB

            // Cập nhật số like/dislike bằng SQL để tránh race condition
            if (Boolean.TRUE.equals(like.getIsLiked())) {
                ratingRepository.incrementTotalLikes(rating.getId());
            } else {
                ratingRepository.incrementTotalDisLikes(rating.getId());
            }

            return likeMapper.toLikeResponse(like);
        }

        // Lấy like đã tồn tại từ DB
        Like existingLike = likeCheck.get();

        // Nếu đã tương tác với cùng giá trị, return luôn
        if (existingLike.getIsLiked().equals(request.getIsLiked())) {
            return likeMapper.toLikeResponse(existingLike);
        }

        // Nếu thay đổi trạng thái like/dislike
        if (Boolean.TRUE.equals(request.getIsLiked())) {
            ratingRepository.incrementTotalLikes(rating.getId());
            ratingRepository.decrementTotalDisLikes(rating.getId());
        } else {
            ratingRepository.incrementTotalDisLikes(rating.getId());
            ratingRepository.decrementTotalLikes(rating.getId());
        }

        // Cập nhật trạng thái like
        existingLike.setIsLiked(request.getIsLiked());
        likeRepository.save(existingLike); // Lưu thay đổi vào DB

        return likeMapper.toLikeResponse(existingLike);
    }


    public LikeResponse updateLike(String likeId, LikeUpdateRequest request){
        var like = likeRepository.findById(likeId).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
        likeMapper.toLikeResponse(like, request);
        like = likeRepository.save(like);

        return likeMapper.toLikeResponse(like);
    }


}
