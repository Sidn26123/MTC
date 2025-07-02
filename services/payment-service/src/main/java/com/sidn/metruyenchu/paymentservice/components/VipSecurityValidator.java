package com.sidn.metruyenchu.paymentservice.components;

import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class VipSecurityValidator {

    public void validateVipAccess(String userId, String requestedUserId) {
        if (!userId.equals(requestedUserId)) {
            throw new AppException(ErrorCode.VIP_ACCESS_DENIED);
        }
    }

    public void validateCouponCode(String couponCode) {
        if (couponCode == null || couponCode.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_COUPON_CODE);
        }

        if (couponCode.length() < 3 || couponCode.length() > 50) {
            throw new AppException(ErrorCode.INVALID_COUPON_CODE);
        }

        if (!couponCode.matches("^[A-Z0-9-_]+$")) {
            throw new AppException(ErrorCode.INVALID_COUPON_CODE);
        }
    }

    public void validatePurchaseAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }

        if (amount.compareTo(new BigDecimal("10000000")) > 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }
    }
}