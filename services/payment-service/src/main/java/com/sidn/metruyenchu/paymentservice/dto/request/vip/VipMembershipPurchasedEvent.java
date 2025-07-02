package com.sidn.metruyenchu.paymentservice.dto.request.vip;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class VipMembershipPurchasedEvent {
    private String userId;
    private String membershipId;
    private String planId;
    private BigDecimal amount;
    private String couponCode;
    private LocalDateTime purchasedAt;
}