package com.sidn.metruyenchu.notificationservice.service;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationTemplateRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationTemplateUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationTemplateResponse;
import com.sidn.metruyenchu.notificationservice.entity.NotificationTemplate;
import com.sidn.metruyenchu.notificationservice.mapper.NotificationTemplateMapper;
import com.sidn.metruyenchu.notificationservice.repository.NotificationTemplateRepository;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationTemplateService {

    NotificationTemplateRepository repository;
    NotificationTemplateMapper mapper;

    public NotificationTemplateResponse create(NotificationTemplateRequest request) {
        NotificationTemplate entity = mapper.toEntity(request);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toResponse(repository.save(entity));
    }

    public NotificationTemplateResponse update(String id, NotificationTemplateUpdateRequest request) {
        NotificationTemplate existing = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        mapper.update(request, existing);
        existing.setUpdatedAt(LocalDateTime.now());
        return mapper.toResponse(repository.save(existing));
    }

    public NotificationTemplateResponse getById(String id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_TEMPLATE_NOT_FOUND)));
    }

    public List<NotificationTemplateResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    public void delete(String id) {
        NotificationTemplate template = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_TEMPLATE_NOT_FOUND));
        repository.delete(template);
    }
}
