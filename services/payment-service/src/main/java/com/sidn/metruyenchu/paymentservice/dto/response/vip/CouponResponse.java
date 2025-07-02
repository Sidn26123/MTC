package com.sidn.metruyenchu.paymentservice.dto.response.vip;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {
    String id;
    String code;
    String name;
    String description;
    CouponType type;
    BigDecimal value;
    BigDecimal minPurchaseAmount;
    BigDecimal maxDiscountAmount;
    Integer usageLimit;
    Integer usageCount;
    Integer userUsageLimit;
    LocalDateTime validFrom;
    LocalDateTime validUntil;
    CouponStatus status;
    CouponApplicableType applicableType;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
