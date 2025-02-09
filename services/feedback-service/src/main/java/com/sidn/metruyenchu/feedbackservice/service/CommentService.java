package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.CommentCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.CommentUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.CommentResponse;
import com.sidn.metruyenchu.feedbackservice.entity.Comment;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.CommentMapper;
import com.sidn.metruyenchu.feedbackservice.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService {
    CommentRepository commentRepository;

    CommentMapper commentMapper;

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
        var comment = commentMapper.toComment(request);
        try{
            comment = commentRepository.save(comment);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.COMMENT_ALREADY_EXISTS);
        }

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
}
