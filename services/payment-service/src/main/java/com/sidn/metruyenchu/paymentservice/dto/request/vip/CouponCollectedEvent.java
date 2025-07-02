package com.sidn.metruyenchu.paymentservice.dto.request.vip;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CouponCollectedEvent {
    private String userId;
    private String couponId;
    private String couponCode;
    private String source;
    private LocalDateTime collectedAt;
}