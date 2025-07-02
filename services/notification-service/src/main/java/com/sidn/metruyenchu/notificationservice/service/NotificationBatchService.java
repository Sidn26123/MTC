package com.sidn.metruyenchu.notificationservice.service;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationBatchRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationBatchUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationBatchResponse;
import com.sidn.metruyenchu.notificationservice.entity.NotificationBatch;
import com.sidn.metruyenchu.notificationservice.mapper.NotificationBatchMapper;
import com.sidn.metruyenchu.notificationservice.repository.NotificationBatchRepository;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationBatchService {

    NotificationBatchRepository repository;
    NotificationBatchMapper mapper;

    public NotificationBatchResponse create(NotificationBatchRequest request) {
        NotificationBatch entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public NotificationBatchResponse update(String id, NotificationBatchUpdateRequest request) {
        NotificationBatch batch = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_BATCH_NOT_FOUND));
        mapper.updateEntityFromDto(request, batch);
        return mapper.toResponse(repository.save(batch));
    }

    public NotificationBatchResponse getById(String id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_BATCH_NOT_FOUND)));
    }

    public List<NotificationBatchResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public void delete(String id) {
        NotificationBatch batch = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_BATCH_NOT_FOUND));
        repository.delete(batch);
    }
}
