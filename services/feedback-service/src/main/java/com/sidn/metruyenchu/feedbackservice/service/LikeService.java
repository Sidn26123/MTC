package com.sidn.metruyenchu.feedbackservice.service;

import com.sidn.metruyenchu.feedbackservice.dto.request.LikeCreationRequest;
import com.sidn.metruyenchu.feedbackservice.dto.request.LikeUpdateRequest;
import com.sidn.metruyenchu.feedbackservice.dto.response.LikeResponse;
import com.sidn.metruyenchu.feedbackservice.exception.AppException;
import com.sidn.metruyenchu.feedbackservice.exception.ErrorCode;
import com.sidn.metruyenchu.feedbackservice.mapper.LikeMapper;
import com.sidn.metruyenchu.feedbackservice.repository.LikeRepository;
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
public class LikeService {
    LikeRepository likeRepository;

    LikeMapper likeMapper;

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

    public void deleteLike(String likeId) {
        if (!likeRepository.existsById(likeId)){
            throw new AppException(ErrorCode.OBJECT_NOT_FOUND);
        }
        likeRepository.deleteById(likeId);
    }

    public LikeResponse createLike(LikeCreationRequest request){
        var like = likeMapper.toLike(request);
        try{
            like = likeRepository.save(like);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.OBJECT_ALREADY_EXISTED);
        }

        return likeMapper.toLikeResponse(like);
    }

    public LikeResponse updateLike(String likeId, LikeUpdateRequest request){
        var like = likeRepository.findById(likeId).orElseThrow(() -> new AppException(ErrorCode.OBJECT_NOT_FOUND));
        likeMapper.toLikeResponse(like, request);
        like = likeRepository.save(like);

        return likeMapper.toLikeResponse(like);
    }


}
