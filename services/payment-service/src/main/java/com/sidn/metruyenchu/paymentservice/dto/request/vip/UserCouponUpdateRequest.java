package com.sidn.metruyenchu.paymentservice.dto.request.vip;
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
public class UserCouponUpdateRequest {
    Integer usageCount;
    UserCouponStatus status;
    LocalDateTime firstUsedAt;
    LocalDateTime lastUsedAt;
}
