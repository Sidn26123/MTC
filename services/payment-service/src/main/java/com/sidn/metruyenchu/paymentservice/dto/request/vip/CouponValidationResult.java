package com.sidn.metruyenchu.paymentservice.dto.request.vip;

import com.sidn.metruyenchu.paymentservice.dto.response.vip.CouponResponse;
import com.sidn.metruyenchu.paymentservice.entity.Coupon;
import com.sidn.metruyenchu.paymentservice.mapper.CouponMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponValidationResult {
    private Boolean valid;
    private String message;
    private Coupon coupon;
    private BigDecimal discountAmount;

    public static CouponValidationResult valid(Coupon coupon, BigDecimal discountAmount) {
        return CouponValidationResult.builder()
                .valid(true)
                .coupon(coupon)
                .discountAmount(discountAmount)
                .build();
    }

    public static CouponValidationResult invalid(String message) {
        return CouponValidationResult.builder()
                .valid(false)
                .message(message)
                .build();
    }
}
