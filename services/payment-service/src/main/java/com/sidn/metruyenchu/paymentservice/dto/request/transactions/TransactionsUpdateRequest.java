package com.sidn.metruyenchu.paymentservice.dto.request.transactions;
import com.sidn.metruyenchu.paymentservice.enums.TransactionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionsUpdateRequest {
    TransactionStatus status;
}