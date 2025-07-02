package com.sidn.metruyenchu.paymentservice.dto.request.vip;

import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipStatusResponse {
    Boolean isVip;
    String planName;
    LocalDateTime expiresAt;
    Integer totalChaptersPerMonth;
    Integer usedChaptersThisMonth;
    Integer remainingChaptersThisMonth;
    Boolean autoRenewal;
}