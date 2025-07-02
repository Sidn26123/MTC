package com.sidn.metruyenchu.paymentservice.dto.request.vip;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponValidationRequest {
    String userId;
    String couponCode;
    BigDecimal purchaseAmount;
    CouponApplicableType applicableType;
}
