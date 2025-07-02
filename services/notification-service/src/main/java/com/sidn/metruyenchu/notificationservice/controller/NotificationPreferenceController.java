package com.sidn.metruyenchu.notificationservice.controller;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationPreferenceRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationPreferenceUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationPreferenceResponse;
import com.sidn.metruyenchu.notificationservice.service.NotificationPreferenceService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification-preferences")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationPreferenceController {

    NotificationPreferenceService service;

    @PostMapping
    public ApiResponse<NotificationPreferenceResponse> create(@RequestBody NotificationPreferenceRequest request) {
        return ApiResponse.<NotificationPreferenceResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<NotificationPreferenceResponse> update(
            @PathVariable String userId,
            @RequestBody NotificationPreferenceUpdateRequest request) {
        return ApiResponse.<NotificationPreferenceResponse>builder()
                .result(service.update(userId, request))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<NotificationPreferenceResponse> getByUserId(@PathVariable String userId) {
        return ApiResponse.<NotificationPreferenceResponse>builder()
                .result(service.getByUserId(userId))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> delete(@PathVariable String userId) {
        service.deleteByUserId(userId);
        return ApiResponse.<Void>builder().build();
    }
}
