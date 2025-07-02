package com.sidn.metruyenchu.paymentservice.mapper;

import com.sidn.metruyenchu.paymentservice.dto.request.wallet.WalletCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.wallet.WalletUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.wallet.WalletResponse;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {
    Wallet toEntity(WalletCreateRequest request);

    WalletResponse toResponse(Wallet wallet);

    List<WalletResponse> toResponses(List<Wallet> wallets);

    void updateEntity(@MappingTarget Wallet wallet, WalletUpdateRequest request);
}