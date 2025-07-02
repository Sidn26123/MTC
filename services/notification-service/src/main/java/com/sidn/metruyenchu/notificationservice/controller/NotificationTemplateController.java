package com.sidn.metruyenchu.notificationservice.controller;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationTemplateRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationTemplateUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationTemplateResponse;
import com.sidn.metruyenchu.notificationservice.service.NotificationTemplateService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-templates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationTemplateController {

    NotificationTemplateService service;

    @PostMapping
    public ApiResponse<NotificationTemplateResponse> create(@RequestBody NotificationTemplateRequest request) {
        return ApiResponse.<NotificationTemplateResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NotificationTemplateResponse> update(
            @PathVariable String id,
            @RequestBody NotificationTemplateUpdateRequest request) {
        return ApiResponse.<NotificationTemplateResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NotificationTemplateResponse> getById(@PathVariable String id) {
        return ApiResponse.<NotificationTemplateResponse>builder()
                .result(service.getById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<NotificationTemplateResponse>> getAll() {
        return ApiResponse.<List<NotificationTemplateResponse>>builder()
                .result(service.getAll())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}

