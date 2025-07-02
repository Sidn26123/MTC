package com.sidn.metruyenchu.paymentservice.dto.request.vip;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponApplicableType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.CouponType;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VipMembershipUpdateRequest {
    LocalDateTime startDate;
    LocalDateTime endDate;
    VipStatus status;
    Boolean autoRenewal;
    Integer chaptersUsedThisMonth;
    LocalDateTime lastChapterReset;
}
