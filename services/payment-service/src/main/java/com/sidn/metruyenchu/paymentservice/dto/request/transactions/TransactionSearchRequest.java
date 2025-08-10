package com.sidn.metruyenchu.paymentservice.dto.request.transactions;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus;
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
public class TransactionSearchRequest {
    String userId;
    String walletId;
    TransactionType type;
    TransactionStatus status;
    String currencyId;
    LocalDateTime fromDate;
    LocalDateTime toDate;
    
    @Builder.Default
    int page = 0;
    
    @Builder.Default
    int size = 20;
    
    @Builder.Default
    String sortBy = "createdAt";
    
    @Builder.Default
    String sortDirection = "DESC";
}