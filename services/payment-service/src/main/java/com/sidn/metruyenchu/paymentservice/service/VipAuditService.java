package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.entity.VipAuditLog;
import com.sidn.metruyenchu.paymentservice.repository.VipAuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VipAuditService {
    
    private final VipAuditLogRepository auditLogRepository;
    
    public void logVipPurchase(String userId, String membershipId, String planId, BigDecimal amount) {
        VipAuditLog log = VipAuditLog.builder()
            .userId(userId)
            .action("PURCHASE_VIP")
            .entityType("VIP_MEMBERSHIP")
            .entityId(membershipId)
            .newValue(String.format("Plan: %s, Amount: %s", planId, amount))
            .build();
        
        auditLogRepository.save(log);
    }
    
    public void logChapterRead(String userId, String chapterId, String membershipId) {
        VipAuditLog log = VipAuditLog.builder()
            .userId(userId)
            .action("OBTAIN_FREE_CHAPTER")
            .entityType("CHAPTER")
            .entityId(chapterId)
            .newValue("Membership: " + membershipId)
            .build();
        
        auditLogRepository.save(log);
    }
    
    public void logCouponUsage(String userId, String couponId, String transactionId, BigDecimal discountAmount) {
        VipAuditLog log = VipAuditLog.builder()
            .userId(userId)
            .action("USE_COUPON")
            .entityType("COUPON")
            .entityId(couponId)
            .newValue(String.format("Transaction: %s, Discount: %s", transactionId, discountAmount))
            .build();
        
        auditLogRepository.save(log);
    }
}