package com.sidn.metruyenchu.paymentservice.dto.request.wallet;
import com.sidn.metruyenchu.paymentservice.enums.WalletStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletUpdateRequest {
    BigDecimal balance;
    WalletStatus walletStatus;
}