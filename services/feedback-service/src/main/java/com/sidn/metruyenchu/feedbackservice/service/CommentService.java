package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.*;
import com.sidn.metruyenchu.feedbackservice.dto.request.feign.CommentNovelRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.projection.GeneralCountProjectionResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.CommentMapper;
import com.sidn.metruyenchu.feedbackservice.repository.CommentRepository;
import com.sidn.metruyenchu.feedbackservice.repository.LikeRepository;
import com.sidn.metruyenchu.feedbackservice.repository.httpclient.NovelClient;
import com.sidn.metruyenchu.feedbackservice.spectifications.CommentSpecification;
import com.sidn.metruyenchu.feedbackservice.utils.EnumUtils;
import com.sidn.metruyenchu.feedbackservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetChapterInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.FeignResponseUtils.callFeignGetNovelInfo;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.feedbackservice.utils.TokenUtils.getUserIdFromToken;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService {
    CommentRepository commentRepository;

    CommentMapper commentMapper;

    NovelClient novelClient;
    private final LikeRepository likeRepository;


    public CommentResponse getComment(String commentId) {
        return commentMapper.toCommentResponse(
                commentRepository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND))
        );
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }

    public CommentResponse createComment(CommentCreationRequest request){
        String userId = getUserIdFromToken(getTokenFromContext());
        request.setCommentedBy(userId);
        var comment = commentMapper.toComment(request);
        //check nếu feedback type không tồn tại trong enum
        if (!EnumUtils.isValidEnum(FeedbackType.class, request.getFeedbackType().toString())){
            throw new AppException(ErrorCode.INVALID_FEEDBACK_TYPE);
        }
        //openfeign call to check novel exist
        NovelResponse novelResponse = callFeignGetNovelInfo(novelClient, request.getNovelId()).getResult();
        ChapterResponse chapterResponse = callFeignGetChapterInfo(novelClient, request.getChapterId()).getResult();

        comment = commentRepository.save(comment);
        novelClient.commentNovel(request.getNovelId(),
                CommentNovelRequest.builder()
                        .chapterId(request.getChapterId())
                        .novelId(request.getNovelId())
                        .chapterIdx(chapterResponse.getChapterIdx())
                        .build());

//        try{
//            comment = commentRepository.save(comment);
//        } catch (DataIntegrityViolationException exception){
//            throw new AppException(ErrorCode.COMMENT_ALREADY_EXISTS);
//        }

        return commentMapper.toCommentResponse(comment);
    }

    /**
     * Action xoá comment
     * @param commentId String
     */
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        //Kieerm tra đã xoá hay chưa
        if (comment.getIsDeleted()){
            throw new AppException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        commentRepository.deleteById(commentId);

        novelClient.deleteComment(comment.getNovelId(),
                CommentNovelRequest.builder()
                        .chapterId(comment.getChapterId())
                        .novelId(comment.getNovelId())
                        .build());
    }

    public CommentResponse updateComment(String commentId, CommentUpdateRequest request){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        commentMapper.updateComment(comment, request);


        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    public Comment getCommentById(String commentId){
        return commentRepository.findById(commentId).orElseThrow(
                () -> new AppException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    public PageResponse<CommentResponse> getCommentInChapter(CommentInChapterGetRequest request){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByChapterId(request.getChapterId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }

    public PageResponse<CommentResponse> getCommentInNovel(CommentOfNovelGetRequest request){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByNovelId(request.getNovelId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }

    public PageResponse<CommentResponse> getCommentOfUser(CommentOfUserGetRequest request){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByCommentedBy(request.getUserId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }

    public PageResponse<CommentResponse> filter(CommentFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        Page<Comment> pageData = commentRepository.findAll(CommentSpecification.filter(request), pageable);
        Page<CommentResponse> commentResponses = pageData.map(commentMapper::toCommentResponse);

        if (request.getIsGetChildCommentCount() != null && request.getIsGetChildCommentCount()) {
            List<String> ids = pageData.getContent()
                    .stream()
                    .map(Comment::getId)
                    .toList();
            //Nếu lấy child comment
            if (request.getParentId() != null) {
                List<GeneralCountProjectionResponse> childComments = commentRepository.countChildComments(ids, FeedbackType.COMMENT);
                childComments.forEach(childComment -> {
                    commentResponses.getContent()
                            .stream()
                            .filter(commentResponse -> commentResponse.getId().equals(childComment.getId()))
                            .findFirst()
                            .ifPresent(commentResponse -> {
                                commentResponse.setTotalComments(childComment.getCount());
                            });
                });
            }
        }

        if (request.getIsGetLikeCount() != null && request.getIsGetLikeCount()) {
            List<String> ids = pageData.getContent()
                    .stream()
                    .map(Comment::getId)
                    .toList();
            log.info("ids: {}", ids);
            if (request.getIsGetChildCommentCount() != null) {
                List<GeneralCountProjectionResponse> childComments = likeRepository.countLike(ids, FeedbackType.COMMENT, true);
                childComments.forEach(childComment -> {
                    log.info("child comment: {} {}", childComment.getId(), childComment.getCount());
                    commentResponses.getContent()
                            .stream()
                            .filter(commentResponse -> commentResponse.getId().equals(childComment.getId()))
                            .findFirst()
                            .ifPresent(commentResponse -> {
                                commentResponse.setTotalLike(childComment.getCount());
                            });
                });
            }
        }


        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(commentResponses.stream().toList())
                .build();

    }

    public void incrementTotalLikes(String commentId) {
        commentRepository.incrementTotalLikes(commentId);
    }

    private List<GeneralCountProjectionResponse> countChildComments(List<String> ratingIds, FeedbackType feedbackType) {
        return commentRepository.countChildComments(ratingIds, feedbackType);
    }

    public void decrementTotalLikes(String commentId) {
        commentRepository.decrementTotalLikes(commentId);
    }

    public void incrementTotalDisLikes(String commentId) {
        commentRepository.incrementTotalDisLikes(commentId);
    }

    public void decrementTotalDisLikes(String commentId) {
        commentRepository.decrementTotalDisLikes(commentId);
    }

}
