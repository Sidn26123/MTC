package com.sidn.metruyenchu.paymentservice.dto.response.wallet;

import com.sidn.metruyenchu.paymentservice.enums.WalletStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletResponse {
    String id;
    String userId;
    BigDecimal balance;
    String currencyId;
    WalletStatus walletStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}