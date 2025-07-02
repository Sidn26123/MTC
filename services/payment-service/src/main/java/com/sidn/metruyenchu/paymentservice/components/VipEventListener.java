package com.sidn.metruyenchu.paymentservice.components;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipChapterReadEvent;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipMembershipPurchasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VipEventListener {

//    private final NotificationService notificationService;
//    private final AnalyticsService analyticsService;
//
//    @EventListener
//    @Async
//    public void handleMembershipPurchased(VipMembershipPurchasedEvent event) {
//        // Send welcome notification
//        notificationService.sendVipWelcomeNotification(event.getUserId(), event.getPlanId());
//
//        // Track analytics
//        analyticsService.trackVipPurchase(event);
//
//        log.info("Processed VIP membership purchased event for user: {}", event.getUserId());
//    }
//
//    @EventListener
//    @Async
//    public void handleChapterRead(VipChapterReadEvent event) {
//        // Check if quota is running low and send warning
//        if (event.getRemainingQuota() <= 10) {
//            notificationService.sendQuotaWarningNotification(
//                event.getUserId(), event.getRemainingQuota());
//        }
//
//        // Track reading analytics
//        analyticsService.trackChapterRead(event);
//    }
//
//    @EventListener
//    @Async
//    public void handleCouponCollected(CouponCollectedEvent event) {
//        // Send notification about new coupon
//        notificationService.sendCouponCollectedNotification(
//            event.getUserId(), event.getCouponCode());
//
//        // Track coupon collection
//        analyticsService.trackCouponCollection(event);
//    }
}