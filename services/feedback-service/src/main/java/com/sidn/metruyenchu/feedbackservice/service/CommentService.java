package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.PageResponse;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentInChapterGetRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.comment.CommentUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.ChapterResponse;
import com.sidn.metruyenchu.feedbackservice.dto.response.feign.NovelResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.CommentMapper;
import com.sidn.metruyenchu.feedbackservice.repository.CommentRepository;
import com.sidn.metruyenchu.feedbackservice.repository.httpclient.NovelClient;
import com.sidn.metruyenchu.feedbackservice.utils.EnumUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

    public CommentResponse getComment(String commentId) {
        return commentMapper.toCommentResponse(
                commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("User not found"))
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
        log.info("chapterId: " + request.getCommentedInChapterId());
        //openfeign call to check novel exist
        NovelResponse novelResponse = callFeignGetNovelInfo(novelClient, request.getCommentedInNovelId()).getResult();
        ChapterResponse chapterResponse = callFeignGetChapterInfo(novelClient, request.getCommentedInChapterId()).getResult();

        comment = commentRepository.save(comment);

//        try{
//            comment = commentRepository.save(comment);
//        } catch (DataIntegrityViolationException exception){
//            throw new AppException(ErrorCode.COMMENT_ALREADY_EXISTS);
//        }

        return commentMapper.toCommentResponse(comment);
    }

    public void deleteComment(String commentId) {
        if (!commentRepository.existsById(commentId)){
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(commentId);
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
        Sort sort = Sort.by(Sort.Direction.DESC, "created_at");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<Comment> comments = commentRepository.findAllByCommentedInChapterId(request.getChapterId(), pageable);
        List<CommentResponse> commentResponses = comments.map(commentMapper::toCommentResponse).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(comments.getTotalPages())
                .totalElements(comments.getTotalElements())
                .data(commentResponses)
                .build();
    }
}
