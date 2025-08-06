package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.response.publish.NovelPublishRequestResponse;
import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import com.sidn.metruyenchu.novelservice.mapper.NovelPublishRequestMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublishRequestCoordinatorService {
    NovelPublishRequestService novelPublishRequestService;
    PublishRequestActionLogService publishRequestActionLogService;

    NovelPublishRequestMapper novelPublishRequestMapper;

    public NovelPublishRequestResponse approveRequest(String id) {
        // B1. Cập nhật trạng thái
        NovelPublishRequest request = novelPublishRequestService.approve(id);

        // B2. Ghi log
        publishRequestActionLogService.approvePublishRequest(request);

        return novelPublishRequestMapper.toResponse(request);
    }
}
