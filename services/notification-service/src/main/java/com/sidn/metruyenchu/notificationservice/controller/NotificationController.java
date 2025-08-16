package com.sidn.metruyenchu.notificationservice.controller;

import com.sidn.metruyenchu.notificationservice.dto.request.NotificationRequest;
import com.sidn.metruyenchu.shared_library.dto.request.notification.*;
import com.sidn.metruyenchu.notificationservice.dto.response.NotificationResponse;
import com.sidn.metruyenchu.notificationservice.mapper.NotificationMapper;
import com.sidn.metruyenchu.notificationservice.service.NotificationService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.sidn.metruyenchu.notificationservice.utils.TokenUtils.getUserIdFromContext;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService service;

    NotificationMapper mapper;

    @PostMapping
    public ApiResponse<NotificationResponse> createNotification(@RequestBody NotificationRequest request) {
        String userId = getUserIdFromContext();
        return ApiResponse.<NotificationResponse>builder()
                .result(mapper.toResponse(service.createNotification(request)))
                .build();
    }

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
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNotification(@PathVariable String id) {
        String userId = getUserIdFromContext();

        service.deleteNotification(id, userId);
        return ApiResponse.<Void>builder().build();
    }

    // New endpoints for specific notification types
    @PostMapping("/novel/liked")
    public ApiResponse<Void> notifyStoryLiked(@RequestBody StoryLikedNotificationRequest request) {
        service.notifyStoryLiked(request.getStoryId(), request.getLikerId(), request.getPublisherId());
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/novel/followed")
    public ApiResponse<Void> notifyStoryFollowed(@RequestBody StoryFollowedNotificationRequest request) {
        service.notifyStoryFollowed(request.getStoryId(), request.getFollowerId(), request.getPublisherId());
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/novel/commented")
    public ApiResponse<Void> notifyStoryCommented(@RequestBody StoryCommentedNotificationRequest request) {
        service.notifyStoryCommented(
                request.getNovelId(),
                request.getCommenterId(),
                request.getPublisherId(),
                request.getCommentContent()
        );
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/novel/rated")
    public ApiResponse<Void> notifyStoryRated(@RequestBody StoryRatedNotificationRequest request) {
        service.notifyStoryRated(
                request.getStoryId(),
                request.getRaterId(),
                request.getPublisherId(),
                request.getRating()
        );
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/novel/reported")
    public ApiResponse<Void> notifyStoryReported(@RequestBody StoryReportedNotificationRequest request) {
        service.notifyStoryReported(
                request.getStoryId(),
                request.getReporterId(),
                request.getPublisherId(),
                request.getReportReason()
        );
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/report/assigned")
    public ApiResponse<Void> notifyReportAssignment(@RequestBody ReportAssignmentNotificationRequest request) {
        service.notifyReportAssignment(request.getAssigneeId());
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/report/status-changed")
    public ApiResponse<Void> notifyReportStatusChange(@RequestBody ReportStatusChangeNotificationRequest request) {
        service.notifyReportStatusChange(request.getReporterId());
        return ApiResponse.<Void>builder().build();
    }


}