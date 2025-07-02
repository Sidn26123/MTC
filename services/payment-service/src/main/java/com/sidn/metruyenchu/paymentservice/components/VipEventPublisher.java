package com.sidn.metruyenchu.paymentservice.components;

import com.sidn.metruyenchu.paymentservice.dto.request.vip.CouponCollectedEvent;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipChapterReadEvent;
import com.sidn.metruyenchu.paymentservice.dto.request.vip.VipMembershipPurchasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VipEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishMembershipPurchased(VipMembershipPurchasedEvent event) {
        log.info("Publishing VIP membership purchased event for user: {}", event.getUserId());
        eventPublisher.publishEvent(event);
    }

    public void publishChapterRead(VipChapterReadEvent event) {
        eventPublisher.publishEvent(event);
    }

    public void publishCouponCollected(CouponCollectedEvent event) {
        log.info("Publishing coupon collected event for user: {}", event.getUserId());
        eventPublisher.publishEvent(event);
    }
}