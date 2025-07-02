package com.sidn.metruyenchu.paymentservice.dto.request.vip;
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
public class CouponUsageUpdateRequest {
    BigDecimal originalAmount;
    BigDecimal discountAmount;
    BigDecimal finalAmount;
}
