package com.sidn.metruyenchu.notificationservice.controller;

import com.sidn.metruyenchu.notificationservice.dto.response.NotificationResponse;
import com.sidn.metruyenchu.notificationservice.service.NotificationService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.sidn.metruyenchu.notificationservice.utils.TokenUtils.getUserIdFromContext;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService service;

    @GetMapping
    public ApiResponse<PageResponse<NotificationResponse>> getNotifications(
            @RequestParam(defaultValue = "false") boolean unreadOnly,
            Pageable pageable) {

        String userId = getUserIdFromContext();
        return ApiResponse.<PageResponse<NotificationResponse>>builder()
                .result(service.getNotificationsForUser(userId, unreadOnly, pageable))
                .build();
    }

    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadCount() {
        String userId = getUserIdFromContext();

        long count = service.getUnreadCount(userId);
        return ApiResponse.<Long>builder()
                .result(count)
                .build();
    }

    @PutMapping("/{id}/read")
    public ApiResponse<Void> markAsRead(@PathVariable String id) {
        String userId = getUserIdFromContext();

        service.markAsRead(id, userId);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/mark-all-read")
    public ApiResponse<Void> markAllAsRead() {
        String userId = getUserIdFromContext();

        service.markAllAsRead(userId);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/{id}/archive")
    public ApiResponse<Void> archiveNotification(@PathVariable String id) {
        String userId = getUserIdFromContext();

        service.archiveNotification(id, userId);
        return ApiResponse.<Void>builder().build();
    }
}