package com.sidn.metruyenchu.paymentservice.dto.request.vip;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VipPlanRequest {
    String name;
    String displayName;
    BigDecimal price;
    String currencyId;
    Integer durationDays;
    Integer freeChaptersPerMonth;
    BigDecimal discountPercentage;
    Boolean active;
    Integer sortOrder;
}
