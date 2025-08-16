package com.sidn.metruyenchu.feedbackservice.repository.httpclient;

import com.sidn.metruyenchu.feedbackservice.configuration.AuthenticationRequestInterceptor;
import com.sidn.metruyenchu.shared_library.dto.request.notification.*;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8095/notification",
        configuration = {AuthenticationRequestInterceptor.class})
public interface NotificationClient {
    
    // Story notifications
    @PostMapping(value = "/notifications/novel/liked", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyStoryLiked(@RequestBody StoryLikedNotificationRequest request);
    
    @PostMapping(value = "/notifications/novel/followed", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyStoryFollowed(@RequestBody StoryFollowedNotificationRequest request);
    
    @PostMapping(value = "/notifications/novel/commented", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyStoryCommented(@RequestBody StoryCommentedNotificationRequest request);
    
    @PostMapping(value = "/notifications/novel/rated", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyStoryRated(@RequestBody StoryRatedNotificationRequest request);
    
    // Report notifications
    @PostMapping(value = "/notifications/novel/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyStoryReported(@RequestBody StoryReportedNotificationRequest request);
    
    @PostMapping(value = "/notifications/report/assigned", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyReportAssignment(@RequestBody ReportAssignmentNotificationRequest request);
    
    @PostMapping(value = "/notifications/report/status-changed", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Void> notifyReportStatusChange(@RequestBody ReportStatusChangeNotificationRequest request);
}