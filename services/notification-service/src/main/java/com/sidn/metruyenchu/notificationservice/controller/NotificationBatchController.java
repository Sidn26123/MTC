package com.sidn.metruyenchu.notificationservice.controller;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationBatchRequest;
import com.sidn.metruyenchu.notificationservice.dto.request.NotificationBatchUpdateRequest;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationBatchResponse;
import com.sidn.metruyenchu.notificationservice.service.NotificationBatchService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-batches")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationBatchController {

    NotificationBatchService service;

    @PostMapping
    public ApiResponse<NotificationBatchResponse> create(@RequestBody NotificationBatchRequest request) {
        return ApiResponse.<NotificationBatchResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NotificationBatchResponse> update(
            @PathVariable String id,
            @RequestBody NotificationBatchUpdateRequest request) {
        return ApiResponse.<NotificationBatchResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NotificationBatchResponse> getById(@PathVariable String id) {
        return ApiResponse.<NotificationBatchResponse>builder()
                .result(service.getById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<NotificationBatchResponse>> getAll() {
        return ApiResponse.<List<NotificationBatchResponse>>builder()
                .result(service.getAll())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
