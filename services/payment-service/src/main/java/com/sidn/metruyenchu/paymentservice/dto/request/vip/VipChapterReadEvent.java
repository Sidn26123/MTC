package com.sidn.metruyenchu.paymentservice.dto.request.vip;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VipChapterReadEvent {
    private String userId;
    private String storyId;
    private String chapterId;
    private String membershipId;
    private Integer remainingQuota;
    private LocalDateTime readAt;
}