package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.like.*;
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
import com.sidn.metruyenchu.feedbackservice.utils.PageUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromToken;


@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
//public class LikeService {
//    LikeRepository likeRepository;
//    CommentRepository commentRepository;
//    RatingRepository ratingRepository;
//    LikeMapper likeMapper;
//    CommentService commentService;
//    RatingService ratingService;
//    CommentMapper commentMapper;
//    RatingMapper ratingMapper;
//    public List<LikeResponse> getAllLikes() {
//        return likeRepository.findAll()
//                .stream()
//                .map(likeMapper::toLikeResponse)
//                .toList();
//    }
//
//    public PageResponse<LikeResponse> getLikes(BaseFilterRequest request){
//        Pageable pageable = PageUtils.from(request);
//
//        var page = likeRepository.findAllByIsDeletedIsFalse(pageable);
//
//        return PageUtils.toPageResponse(page, likeMapper::toLikeResponse, request.getPage());
//
//    }
//    public LikeResponse getLike(String likeId) {
//        return likeMapper.toLikeResponse(
//                likeRepository.findById(likeId).orElseThrow(() -> new RuntimeException("Like not found"))
//        );
//    }
//
//    public Like getLikeById(String likeId){
//        return likeRepository.findById(likeId).orElseThrow(
//                () -> new AppException(ErrorCode.OBJECT_NOT_FOUND)
//        );
//    }
//
//
////    public Like getLike(LikeFi)
//
//    public void deleteLike(String likeId) {
//        if (!likeRepository.existsById(likeId)){
//            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
//        }
//        likeRepository.deleteById(likeId);
//    }
//
//    public Integer checkUserAlreadyActiveComment(String userId, String commentId){
//        Like like = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
//                commentId,
//                userId,
//                FeedbackType.COMMENT
//        ).orElse(null);
//        if (like == null || like.getId() == null){
//            return -1;
//        }
//        return Integer.parseInt(String.valueOf(like.getIsLiked()));
//
//    }
//
//    public LikeResponse createLike(LikeCreationRequest request){
//        var like = likeMapper.toLike(request);
//
//        Object object;
//        if (request.getFeedbackType() == FeedbackType.COMMENT){
//            object = commentService.getComment(request.getParentId());
//        }
//
//        else if (request.getFeedbackType() == FeedbackType.RATING){
//            object = ratingService.getReview(request.getParentId());
//        }
//
//
//
//        try{
//            like = likeRepository.save(like);
//        } catch (DataIntegrityViolationException exception){
//            throw new AppException(ErrorCode.OBJECT_ALREADY_EXISTED);
//        }
//
//        return likeMapper.toLikeResponse(like);
//    }
//
//
////    @Transactional
////    public LikeResponse likeComment(LikeCreationRequest request) {
////        String userId = getUserIdFromToken(getTokenFromContext());
////        request.setLikedBy(userId);
////
////        // Kiểm tra comment tồn tại
////        Comment comment = commentService.getCommentById(request.getParentId());
////
////        // Kiểm tra user đã tương tác với comment chưa
////        Optional<Like> likeCheck = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
////                request.getParentId(),
////                request.getLikedBy(),
////                FeedbackType.COMMENT
////        );
//////        log.info("Like check: {}", likeCheck.get().getId());
////
////        if (!likeCheck.isPresent()) {
////            // Tạo like mới vì chưa có tương tác trước đó
////            Like like = likeMapper.toLike(request);
////
////            like = likeRepository.save(like); // Lưu like mới vào DB
////
////            // Cập nhật số like/dislike bằng SQL để tránh race condition
////            if (Boolean.TRUE.equals(like.getIsLiked())) {
////                commentRepository.incrementTotalLikes(comment.getId());
////            } else {
////                commentRepository.incrementTotalDisLikes(comment.getId());
////            }
////
////            return likeMapper.toLikeResponse(like);
////        }
////
////        // Lấy like đã tồn tại từ DB
////        Like existingLike = likeCheck.get();
////
////        // Nếu đã tương tác với cùng giá trị, return luôn
////        if (existingLike.getIsLiked().equals(request.getIsLiked())) {
////            log.info("a");
////            return likeMapper.toLikeResponse(existingLike);
////        }
////        log.info("B");
////        // Nếu thay đổi trạng thái like/dislike
////        if (Boolean.TRUE.equals(request.getIsLiked())) {
////            commentRepository.incrementTotalLikes(comment.getId());
////            commentRepository.decrementTotalDisLikes(comment.getId());
////        } else {
////            commentRepository.incrementTotalDisLikes(comment.getId());
////            commentRepository.decrementTotalLikes(comment.getId());
////        }
////
////        // Cập nhật trạng thái like
////        existingLike.setIsLiked(request.getIsLiked());
////        log.info("C: {}", existingLike.getId());
////        likeRepository.save(existingLike); // Lưu thay đổi vào DB
////
////        return likeMapper.toLikeResponse(existingLike);
////    }
//@Transactional
//public LikeResponse likeComment(LikeCreationRequest request) {
//    String userId = getUserIdFromToken(getTokenFromContext());
//    request.setLikedBy(userId);
//
//    // Kiểm tra comment tồn tại
//    Comment comment = commentService.getCommentById(request.getParentId());
//
//    // Kiểm tra user đã tương tác với comment chưa
//    Optional<Like> likeCheck = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
//            request.getParentId(),
//            request.getLikedBy(),
//            FeedbackType.COMMENT
//    );
//
//    if (!likeCheck.isPresent()) {
//
//        Like like = likeMapper.toLike(request);
//        like = likeRepository.save(like);
//
//        //Neu la like
//        if (Boolean.TRUE.equals(like.getIsLiked())) {
//            commentRepository.incrementTotalLikes(comment.getId());
//        } else {
//            commentRepository.incrementTotalDisLikes(comment.getId());
//        }
//
//        return likeMapper.toLikeResponse(like);
//    }
//
//    Like existingLike = likeCheck.get();
//    log.info("Existing like found: id={}, isLiked={}", existingLike.getId(), existingLike.getIsLiked());
//
//    if (existingLike.getIsLiked().equals(request.getIsLiked())) {
//        log.info("Same like/dislike state submitted again, skipping update. likeId={}, isLiked={}", existingLike.getId(), existingLike.getIsLiked());
//        return likeMapper.toLikeResponse(existingLike);
//    }
//
//    log.info("Changing like state for likeId={}, from {} to {}", existingLike.getId(), existingLike.getIsLiked(), request.getIsLiked());
//
//    if (Boolean.TRUE.equals(request.getIsLiked())) {
//        commentRepository.incrementTotalLikes(comment.getId());
//        commentRepository.decrementTotalDisLikes(comment.getId());
//        log.info("Switched to like: updated comment stats.");
//    } else {
//        commentRepository.incrementTotalDisLikes(comment.getId());
//        commentRepository.decrementTotalLikes(comment.getId());
//        log.info("Switched to dislike: updated comment stats.");
//    }
//
//    existingLike.setIsLiked(request.getIsLiked());
//    likeRepository.save(existingLike);
//    log.info("Updated like state saved: likeId={}, newState={}", existingLike.getId(), existingLike.getIsLiked());
//
//    return likeMapper.toLikeResponse(existingLike);
//}
//
//    @Transactional
//    public LikeResponse likeRating(LikeCreationRequest request) {
//        String userId = getUserIdFromToken(getTokenFromContext());
//        request.setLikedBy(userId);
//
//        // Kiểm tra rating tồn tại
//        Rating rating = ratingService.getRatingById(request.getParentId());
//
//        // Kiểm tra user đã tương tác với rating chưa
//        Optional<Like> likeCheck = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
//                request.getParentId(),
//                request.getLikedBy(),
//                FeedbackType.RATING
//        );
//        if (likeCheck.isEmpty()) {
//            // Tạo like mới vì chưa có tương tác trước đó
//            Like like = likeMapper.toLike(request);
//            like.setFeedbackType(FeedbackType.RATING);
//            like = likeRepository.save(like); // Lưu like mới vào DB
//
//            // Cập nhật số like/dislike bằng SQL để tránh race condition
//            if (Boolean.TRUE.equals(like.getIsLiked())) {
//
//                ratingRepository.incrementTotalLikes(rating.getId());
//            } else {
//                ratingRepository.incrementTotalDisLikes(rating.getId());
//            }
//
//            return likeMapper.toLikeResponse(like);
//        }
//
//        // Lấy like đã tồn tại từ DB
//        Like existingLike = likeCheck.get();
//
//        // Nếu đã tương tác với cùng giá trị, return luôn
//        if (existingLike.getIsLiked().equals(request.getIsLiked())) {
//            return likeMapper.toLikeResponse(existingLike);
//        }
//
//        // Nếu thay đổi trạng thái like/dislike
//        if (Boolean.TRUE.equals(request.getIsLiked())) {
//            ratingRepository.incrementTotalLikes(rating.getId());
//            ratingRepository.decrementTotalDisLikes(rating.getId());
//        } else {
//            ratingRepository.incrementTotalDisLikes(rating.getId());
//            ratingRepository.decrementTotalLikes(rating.getId());
//        }
//
//        // Cập nhật trạng thái like
//        existingLike.setIsLiked(request.getIsLiked());
//
//        likeRepository.save(existingLike); // Lưu thay đổi vào DB
//
//        return likeMapper.toLikeResponse(existingLike);
//    }
//
//
//    @Transactional
//    public LikeResponse updateLike(String likeId, LikeUpdateRequest request){
//        var like = likeRepository.findById(likeId).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
//        likeMapper.toLikeResponse(like, request);
//        like = likeRepository.save(like);
//
//        return likeMapper.toLikeResponse(like);
//    }
//
//    public Like findLikeOfUserOfComment(String commentId, String userId){
//        Like like = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
//                commentId,
//                userId,
//                FeedbackType.COMMENT
//        ).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
//        return like;
//    }
//
//    /**
//     * Unlike comment
//     * @param request UnLikeCommentRequest
//     * @return
//     */
//    @Transactional
//    public LikeResponse unlikeComment(UnLikeCommentRequest request) {
//        //Kiểm tra nếu có userId thì bỏ qua, nếu = null thì lấy từ token
//        if (request.getUserId() == null){
//            request.setUserId(getUserIdFromToken(getTokenFromContext()));
//        }
//        String commentId = request.getCommentId();
//        if (commentId == null) {
//            throw new AppException(ErrorCode.INVALID_REQUEST); // yêu cầu thiếu commentId
//        }
//        Like like = null;
//        //Check commentId có tồn tại không
//        Comment comment = commentService.getCommentById(request.getCommentId());
//        //Check likeId có tồn tại không
//        like = findLikeOfUserOfComment(request.getCommentId(), request.getUserId());
//        //Giảm số lượng like/dislike
//        commentService.decrementTotalLikes(request.getCommentId());
//        //Nếu có thì xóa
//        likeRepository.save(like);
//
////        return null;
//        return likeMapper.toLikeResponse(like);
//    }
//
//    /**
//     * Unlike rating
//     * @param request
//     * @return
//     */
//    @Transactional
//    public LikeResponse unlikeRating(UnLikeRatingRequest request) {
//        //Kiểm tra nếu có userId thì bỏ qua, nếu = null thì lấy từ token
//        if (request.getUserId() == null) {
//            request.setUserId(getUserIdFromToken(getTokenFromContext()));
//        }
//        String ratingId = request.getRatingId();
//        if (ratingId == null) {
//            throw new AppException(ErrorCode.INVALID_REQUEST); // yêu cầu thiếu commentId
//        }
//        Like like = null;
//        //Check rating có tồn tại không
////        Rating rating = ratingService.getRating(request.getRatingId());
//        //Check likeId có tồn tại không
//        like = findLikeOfUserOfRating(request.getRatingId(), request.getUserId());
//        //Giảm số lượng like/dislike
//        ratingService.decrementTotalLikes(request.getRatingId());
//        //Nếu có thì xóa
//        likeRepository.save(like);
//
//        return likeMapper.toLikeResponse(like);
//    }
//
//    public LikeResponse toggleLikeInRating(ToggleLikeRequest request){
//        Rating rating = ratingService.getRatingById(request.getParentId());
//        String userId = getUserIdFromToken(getTokenFromContext());
//    }
//
//
//
//    private Like findLikeOfUserOfRating(String ratingId, String userId) {
//        return likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
//                ratingId,
//                userId,
//                FeedbackType.RATING
//        ).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
//    }
//}
public class LikeService {

    LikeRepository likeRepository;
    CommentRepository commentRepository;
    RatingRepository ratingRepository;
    LikeMapper likeMapper;
    CommentService commentService;
    RatingService ratingService;

    // === QUERY METHODS ===

    public List<LikeResponse> getAllLikes() {
        return likeRepository.findAll()
                .stream()
                .map(likeMapper::toLikeResponse)
                .toList();
    }

    public PageResponse<LikeResponse> getLikes(BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        var page = likeRepository.findAllByIsDeletedIsFalse(pageable);
        return PageUtils.toPageResponse(page, likeMapper::toLikeResponse, request.getPage());
    }

    public LikeResponse getLike(String likeId) {
        Like like = getLikeById(likeId);
        return likeMapper.toLikeResponse(like);
    }

    public Like getLikeById(String likeId) {
        return likeRepository.findById(likeId)
                .orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
    }

    // === LIKE INTERACTION CHECK ===

    /**
     * Kiểm tra trạng thái like/dislike của user với comment
     *
     * @return -1 nếu chưa có tương tác, 0 nếu dislike, 1 nếu like
     */
    public Integer checkUserLikeStatusForComment(String userId, String commentId) {
        return likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                        commentId, userId, FeedbackType.COMMENT)
                .map(like -> like.getIsLiked() ? 1 : 0)
                .orElse(-1);
    }

    /**
     * Kiểm tra trạng thái like/dislike của user với rating
     *
     * @return -1 nếu chưa có tương tác, 0 nếu dislike, 1 nếu like
     */
    public Integer checkUserLikeStatusForRating(String userId, String ratingId) {
        return likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                        ratingId, userId, FeedbackType.RATING)
                .map(like -> like.getIsLiked() ? 1 : 0)
                .orElse(-1);
    }

    // === LIKE/DISLIKE OPERATIONS ===

    @Transactional
    public LikeResponse toggleLikeForComment(LikeCreationRequest request) {
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setLikedBy(userId);
        request.setFeedbackType(FeedbackType.COMMENT);

        // Validate comment exists
        Comment comment = commentService.getCommentById(request.getParentId());

        return processLikeToggle(request, comment.getId(), FeedbackType.COMMENT);
    }

    @Transactional
    public LikeResponse toggleLikeForRating(LikeCreationRequest request) {
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setLikedBy(userId);
        request.setFeedbackType(FeedbackType.RATING);

        // Validate rating exists
        Rating rating = ratingService.getRatingById(request.getParentId());

        return processLikeToggle(request, rating.getId(), FeedbackType.RATING);
    }

    /**
     * Core logic for processing like/dislike toggle
     */
    private LikeResponse processLikeToggle(LikeCreationRequest request, String parentId, FeedbackType feedbackType) {
        Optional<Like> existingLike = likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                parentId, request.getLikedBy(), feedbackType);

        if (existingLike.isEmpty()) {
            return createNewLike(request, parentId, feedbackType);
        }

        return updateExistingLike(existingLike.get(), request, feedbackType);
    }

    private LikeResponse createNewLike(LikeCreationRequest request, String parentId, FeedbackType feedbackType) {
        Like like = likeMapper.toLike(request);
        like.setFeedbackType(feedbackType);
        like = likeRepository.save(like);

        updateCounters(parentId, feedbackType, request.getIsLiked(), null);

        log.info("Created new like: parentId={}, userId={}, isLiked={}, feedbackType={}",
                parentId, request.getLikedBy(), request.getIsLiked(), feedbackType);

        return likeMapper.toLikeResponse(like);
    }

    private LikeResponse updateExistingLike(Like existingLike, LikeCreationRequest request, FeedbackType feedbackType) {
        Boolean oldState = existingLike.getIsLiked();
        Boolean newState = request.getIsLiked();

        // Same state - no change needed
        if (oldState.equals(newState)) {
            log.debug("Like state unchanged: likeId={}, state={}", existingLike.getId(), oldState);
            return likeMapper.toLikeResponse(existingLike);
        }

        // Update like state and counters
        existingLike.setIsLiked(newState);
        likeRepository.save(existingLike);

        updateCounters(existingLike.getParentId(), feedbackType, newState, oldState);

        log.info("Updated like state: likeId={}, from {} to {}", existingLike.getId(), oldState, newState);

        return likeMapper.toLikeResponse(existingLike);
    }

    /**
     * Update like/dislike counters based on state change
     */
    private void updateCounters(String parentId, FeedbackType feedbackType, Boolean newState, Boolean oldState) {
        if (feedbackType == FeedbackType.COMMENT) {
            updateCommentCounters(parentId, newState, oldState);
        } else if (feedbackType == FeedbackType.RATING) {
            updateRatingCounters(parentId, newState, oldState);
        }
    }

    private void updateCommentCounters(String commentId, Boolean newState, Boolean oldState) {
        if (oldState == null) {
            // New like/dislike
            if (Boolean.TRUE.equals(newState)) {
                commentRepository.incrementTotalLikes(commentId);
            } else {
                commentRepository.incrementTotalDisLikes(commentId);
            }
        } else {
            // State change
            if (Boolean.TRUE.equals(newState)) {
                commentRepository.incrementTotalLikes(commentId);
                commentRepository.decrementTotalDisLikes(commentId);
            } else {
                commentRepository.incrementTotalDisLikes(commentId);
                commentRepository.decrementTotalLikes(commentId);
            }
        }
    }

    private void updateRatingCounters(String ratingId, Boolean newState, Boolean oldState) {
        if (oldState == null) {
            // New like/dislike
            if (Boolean.TRUE.equals(newState)) {
                ratingRepository.incrementTotalLikes(ratingId);
            } else {
                ratingRepository.incrementTotalDisLikes(ratingId);
            }
        } else {
            // State change
            if (Boolean.TRUE.equals(newState)) {
                ratingRepository.incrementTotalLikes(ratingId);
                ratingRepository.decrementTotalDisLikes(ratingId);
            } else {
                ratingRepository.incrementTotalDisLikes(ratingId);
                ratingRepository.decrementTotalLikes(ratingId);
            }
        }
    }

    // === UNLIKE OPERATIONS ===

    @Transactional
    public Void unlikeComment(String commentId, String userId) {
        if (userId == null) {
            userId = getUserIdFromToken(getTokenFromContext());
        }

        validateCommentExists(commentId);
        Like like = findUserLikeForComment(commentId, userId);

        removeLikeAndUpdateCounters(like, FeedbackType.COMMENT);

        log.info("Removed like for comment: commentId={}, userId={}", commentId, userId);

        return null;
    }

    @Transactional
    public Void unlikeRating(String ratingId, String userId) {
        if (userId == null) {
            userId = getUserIdFromToken(getTokenFromContext());
        }

        validateRatingExists(ratingId);
        Like like = findUserLikeForRating(ratingId, userId);

        removeLikeAndUpdateCounters(like, FeedbackType.RATING);

        log.info("Removed like for rating: ratingId={}, userId={}", ratingId, userId);

        return null;
    }

    private void removeLikeAndUpdateCounters(Like like, FeedbackType feedbackType) {
        String parentId = like.getParentId();
        Boolean wasLiked = like.getIsLiked();

        // Soft delete
        like.setIsDeleted(true);
        likeRepository.save(like);

        // Update counters
        if (feedbackType == FeedbackType.COMMENT) {
            if (Boolean.TRUE.equals(wasLiked)) {
                commentRepository.decrementTotalLikes(parentId);
            } else {
                commentRepository.decrementTotalDisLikes(parentId);
            }
        } else if (feedbackType == FeedbackType.RATING) {
            if (Boolean.TRUE.equals(wasLiked)) {
                ratingRepository.decrementTotalLikes(parentId);
            } else {
                ratingRepository.decrementTotalDisLikes(parentId);
            }
        }
    }

    // === HELPER METHODS ===

    private void validateCommentExists(String commentId) {
        commentService.getCommentById(commentId);
    }

    private void validateRatingExists(String ratingId) {
        ratingService.getRatingById(ratingId);
    }

    private Like findUserLikeForComment(String commentId, String userId) {
        return likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                        commentId, userId, FeedbackType.COMMENT)
                .orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
    }

    private Like findUserLikeForRating(String ratingId, String userId) {
        return likeRepository.findLikeByParentIdAndLikedByAndFeedbackTypeAndIsDeletedIsFalse(
                        ratingId, userId, FeedbackType.RATING)
                .orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
    }

    // === LEGACY SUPPORT (Deprecated) ===

    @Deprecated
    public LikeResponse createLike(LikeCreationRequest request) {
        if (request.getFeedbackType() == FeedbackType.COMMENT) {
            return toggleLikeForComment(request);
        } else if (request.getFeedbackType() == FeedbackType.RATING) {
            return toggleLikeForRating(request);
        }
        throw new AppException(ErrorCode.INVALID_REQUEST);
    }

    @Deprecated
    public LikeResponse likeComment(LikeCreationRequest request) {
        return toggleLikeForComment(request);
    }

    @Deprecated
    public LikeResponse likeRating(LikeCreationRequest request) {
        return toggleLikeForRating(request);
    }

    public void deleteLike(String likeId) {
        if (!likeRepository.existsById(likeId)) {
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }
        likeRepository.deleteById(likeId);
    }

    @Transactional
    public LikeResponse updateLike(String likeId, LikeUpdateRequest request) {
        Like like = getLikeById(likeId);
        likeMapper.update(like, request);
        like = likeRepository.save(like);
        return likeMapper.toLikeResponse(like);
    }

}