package com.sidn.metruyenchu.notificationservice.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationPreferenceRequest {
    String userId;

    Boolean storyInteractionsEnabled;
    Boolean storyInteractionsEmail;
    Boolean storyInteractionsPush;

    Boolean publisherActivitiesEnabled;
    Boolean publisherActivitiesEmail;
    Boolean publisherActivitiesPush;

    Boolean systemNotificationsEnabled;
    Boolean systemNotificationsEmail;
    Boolean systemNotificationsPush;

    Boolean userActivitiesEnabled;
    Boolean userActivitiesEmail;
    Boolean userActivitiesPush;

    LocalTime quietHoursStart;
    LocalTime quietHoursEnd;
    String timezone;
}
