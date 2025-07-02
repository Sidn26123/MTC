package com.sidn.metruyenchu.notificationservice.service;

import com.sidn.metruyenchu.notificationservice.dto.request.NotificationPreferenceRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationPreferenceUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationPreferenceResponse;
import com.sidn.metruyenchu.notificationservice.entity.NotificationPreference;
import com.sidn.metruyenchu.notificationservice.mapper.NotificationPreferenceMapper;
import com.sidn.metruyenchu.notificationservice.repository.NotificationPreferenceRepository;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationPreferenceService {

    NotificationPreferenceRepository repository;
    NotificationPreferenceMapper mapper;

    public NotificationPreferenceResponse create(NotificationPreferenceRequest request) {
        if (repository.findByUserId(request.getUserId()).isPresent()) {
            throw new AppException(ErrorCode.DUPLICATE_USER_NOTIFICATION_PREFERENCE);
        }
        NotificationPreference entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public NotificationPreferenceResponse update(String userId, NotificationPreferenceUpdateRequest request) {
        NotificationPreference preference = repository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_PREFERENCE_NOT_FOUND));
        mapper.update(request, preference);
        return mapper.toResponse(repository.save(preference));
    }

    public NotificationPreferenceResponse getByUserId(String userId) {
        return mapper.toResponse(repository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_PREFERENCE_NOT_FOUND)));
    }

    public void deleteByUserId(String userId) {
        NotificationPreference preference = repository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_PREFERENCE_NOT_FOUND));
        repository.delete(preference);
    }
}
