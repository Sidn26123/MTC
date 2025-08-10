package com.sidn.metruyenchu.paymentservice.dto.request.transactions;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionsUpdateRequest {
    @NotNull(message = "Trạng thái không được để trống")
    TransactionStatus status;
}