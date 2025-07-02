package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelRequest;
import com.sidn.metruyenchu.novelservice.dto.request.markednovel.MarkedNovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.markedNovel.MarkedNovelResponse;
import com.sidn.metruyenchu.novelservice.entity.MarkedNovel;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.MarkedNovelMapper;
import com.sidn.metruyenchu.novelservice.repository.MarkedNovelRepository;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MarkedNovelService {
    MarkedNovelRepository markedNovelRepository;
    MarkedNovelMapper markedNovelMapper;
    private final NovelRepository novelRepository;

    //get by id

    /**
     * Lấy thông tin truyện đã đánh dấu theo id
     * @param id UUID của truyện đã đánh dấu cần lấy
     * @return MarkedNovelResponse thông tin truyện đã đánh dấu
     */
    public MarkedNovelResponse getById(String id) {
        MarkedNovel markedNovel = markedNovelRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MARKED_NOVEL_NOT_FOUND));

        return markedNovelMapper.toResponse(markedNovel);
    }


    //get all

    /**
     * Lấy tất cả truyện đã đánh dấu
     * @param request
     * @return
     */
    public PageResponse<MarkedNovelResponse> getAll(BaseFilterRequest request){
        Pageable pageable = PageUtils.from(request);

        var pageData = markedNovelRepository.findAll(pageable);


        return PageUtils.toPageResponse(
                pageData,
                markedNovelMapper::toResponse,
                request.getPage()
        );
    }

    public PageResponse<MarkedNovelResponse> getAllByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var pageData = markedNovelRepository.findAllByUserId(userId, pageable);
        return PageUtils.toPageResponse(pageData, markedNovelMapper::toResponse, page);
    }
    //create

    /**
     * Tạo mới truyện đã đánh dấu, nếu đã có thi throw lỗi
     * @param request
     * @return
     */
    public MarkedNovelResponse createMarkedNovel(MarkedNovelCreateRequest request) {
//        MarkedNovel markedNovel = markedNovelRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId());
        Optional<MarkedNovel> checkMarkedNovel = markedNovelRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId());

        // Nếu đã có thì cập nhật lại
        if (checkMarkedNovel.isPresent()) {
            throw new AppException(ErrorCode.MARKED_NOVEL_ALREADY_EXISTS);
        }

        MarkedNovel markedNovel = markedNovelMapper.toEntity(request);

        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        markedNovel.setNovel(novel);
        try {
            markedNovel = markedNovelRepository.save(markedNovel);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return markedNovelMapper.toResponse(markedNovel);
    }

    /**
     * Đánh dấu truyện cho người dùng.
     * Nếu đã tồn tại đánh dấu thì cập nhật, nếu chưa thì tạo mới.
     *
     * @param request MarkedNovelRequest thông tin đánh dấu truyện
     * @return MarkedNovelResponse thông tin sau khi đánh dấu
     */
    public MarkedNovelResponse markNovel(MarkedNovelRequest request) {
        // Tìm xem đã có đánh dấu cho truyện này chưa
        Optional<MarkedNovel> existingMarkedNovel = markedNovelRepository.findByUserIdAndNovelId(
                request.getUserId(), request.getNovelId()
        );

        if (existingMarkedNovel.isPresent()) {
            // Nếu đã tồn tại, cập nhật thông tin đánh dấu
            MarkedNovel markedNovel = existingMarkedNovel.get();
            markedNovelMapper.updateEntity(markedNovel, request);

            try {
                markedNovel = markedNovelRepository.save(markedNovel);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UNKNOWN_ERROR);
            }

            return markedNovelMapper.toResponse(markedNovel);
        } else {
            // Nếu chưa tồn tại, tạo mới
            MarkedNovel markedNovel = markedNovelMapper.toEntity(request);

            // Gán truyện tương ứng
            Novel novel = novelRepository.findById(request.getNovelId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
            markedNovel.setNovel(novel);

            try {
                markedNovel = markedNovelRepository.save(markedNovel);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UNKNOWN_ERROR);
            }

            return markedNovelMapper.toResponse(markedNovel);
        }
    }


    //update

    /**
     * Cập nhật thông tin truyện đã đánh dấu
     * @param markedNovelId UUID của truyện đã đánh dấu
     * @param request thông tin cập nhật
     * @return MarkedNovelResponse thông tin truyện đã đánh dấu sau khi cập nhật
     */
    public MarkedNovelResponse updateMarkedNovel(String markedNovelId, MarkedNovelUpdateRequest request) {
        MarkedNovel markedNovel = markedNovelRepository.findById(markedNovelId)
                .orElseThrow(() -> new AppException(ErrorCode.MARKED_NOVEL_NOT_FOUND));

        markedNovelMapper.updateEntity(markedNovel, request);

        try {
            markedNovel = markedNovelRepository.save(markedNovel);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return markedNovelMapper.toResponse(markedNovel);
    }

    //delete

    /**
     * Xoá truyện đã đánh dấu
     * @param markedNovelId UUID của truyện đã đánh dấu
     * @return null
     */
    public Void deleteMarkedNovel(String markedNovelId) {
        MarkedNovel markedNovel = markedNovelRepository.findById(markedNovelId)
                .orElseThrow(() -> new AppException(ErrorCode.MARKED_NOVEL_NOT_FOUND));

        try {
            markedNovelRepository.delete(markedNovel);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return null;
    }

    public Void softDeleteMarkedNovel(String markedNovelId) {
        MarkedNovel markedNovel = markedNovelRepository.findById(markedNovelId)
                .orElseThrow(() -> new AppException(ErrorCode.MARKED_NOVEL_NOT_FOUND));

        try {
            markedNovel.setIsDeleted(true);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return null;
    }


}
