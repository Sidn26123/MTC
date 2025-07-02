package com.sidn.metruyenchu.paymentservice.mapper;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.transactions.TransactionsResponse;
import com.sidn.metruyenchu.paymentservice.entity.Transactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring",
         nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
         uses = {WalletMapper.class})
public interface TransactionsMapper {
    @Mapping(source = "wallet.id", target = "walletId")
    TransactionsResponse toResponse(Transactions transactions);

    Transactions toEntity(TransactionsCreateRequest request);

    List<TransactionsResponse> toResponses(List<Transactions> transactions);

    void updateEntity(@MappingTarget Transactions transactions, TransactionsUpdateRequest request);
}
