package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.publish.PublishRequestActionLogCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.publish.PublishRequestActionLogUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.publish.NovelPublishRequestResponse;
import com.sidn.metruyenchu.novelservice.dto.response.publish.PublishRequestActionLogResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import com.sidn.metruyenchu.novelservice.entity.PublishRequestActionLog;
import com.sidn.metruyenchu.novelservice.enums.PublishRequestAction;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.PublishRequestActionLogMapper;
import com.sidn.metruyenchu.novelservice.repository.NovelPublishRequestRepository;
import com.sidn.metruyenchu.novelservice.repository.PublishRequestActionLogRepository;
import com.sidn.metruyenchu.novelservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sidn.metruyenchu.novelservice.utils.TokenUtils.getUserIdFromContext;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublishRequestActionLogService {
    PublishRequestActionLogMapper publishRequestActionLogMapper;
    PublishRequestActionLogRepository publishRequestActionLogRepository;
    NovelPublishRequestService novelPublishRequestService;
    //Tao publish request action log
    public PublishRequestActionLogResponse create(PublishRequestActionLogCreationRequest request) {
        //Đặt trạng thái của yêu cầu là đã nhận
        request.setAction(PublishRequestAction.TAKEN);

        //Lấy yêu cầu
        NovelPublishRequest novelPublishRequest = novelPublishRequestService.getEntityById(request.getNovelPublishRequestId());

        //Tạo entity PublishRequestActionLog
        PublishRequestActionLog publishRequestActionLog = publishRequestActionLogMapper.toEntity(request);
        publishRequestActionLog.setNovelPublishRequest(novelPublishRequest);

        publishRequestActionLog = publishRequestActionLogRepository.save(publishRequestActionLog);
        return publishRequestActionLogMapper.toResponse(
                publishRequestActionLog
        );
    }

    public PublishRequestActionLogResponse getById(String id) {
        PublishRequestActionLog publishRequestActionLog = publishRequestActionLogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISH_REQUEST_ACTION_LOG_NOT_FOUND));

        return publishRequestActionLogMapper.toResponse(publishRequestActionLog);
    }

    public PageResponse<PublishRequestActionLogResponse> getAll(BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        var pageData = publishRequestActionLogRepository.findAll(pageable);
        return PageUtils.toPageResponse(pageData, publishRequestActionLogMapper::toResponse, request.getPage());
    }

    public PageResponse<PublishRequestActionLogResponse> getAllByNovelId(String novelId) {
        return null;
    }

    public PublishRequestActionLogResponse update(String id, PublishRequestActionLogUpdateRequest request) {
        PublishRequestActionLog publishRequestActionLog = publishRequestActionLogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISH_REQUEST_ACTION_LOG_NOT_FOUND));
        publishRequestActionLogMapper.update(publishRequestActionLog, request);
        publishRequestActionLog = publishRequestActionLogRepository.save(publishRequestActionLog);
        return publishRequestActionLogMapper.toResponse(publishRequestActionLog);
    }

    public void delete(String id) {
        PublishRequestActionLog publishRequestActionLog = publishRequestActionLogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISH_REQUEST_ACTION_LOG_NOT_FOUND));
        publishRequestActionLogRepository.delete(publishRequestActionLog);

    }

    public PublishRequestActionLogResponse approvePublishRequest(String id) {
        PublishRequestActionLog publishRequestActionLog = publishRequestActionLogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISH_REQUEST_ACTION_LOG_NOT_FOUND));
        publishRequestActionLog.setAction(PublishRequestAction.APPROVED);
        String userApproved = getUserIdFromContext();
        publishRequestActionLog.setActionBy(userApproved);
        publishRequestActionLog = publishRequestActionLogRepository.save(publishRequestActionLog);
        return publishRequestActionLogMapper.toResponse(publishRequestActionLog);
    }


}
