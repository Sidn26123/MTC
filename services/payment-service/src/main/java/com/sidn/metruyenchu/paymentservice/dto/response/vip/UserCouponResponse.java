package com.sidn.metruyenchu.paymentservice.dto.response.vip;

import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.UserCouponStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCouponResponse {
    String id;
    String userId;
    String couponId;
    Integer usageCount;
    UserCouponStatus status;
    String obtainedFrom;
    LocalDateTime obtainedAt;
    LocalDateTime firstUsedAt;
    LocalDateTime lastUsedAt;
}
