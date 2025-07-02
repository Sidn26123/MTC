package com.sidn.metruyenchu.paymentservice.mapper;

import com.sidn.metruyenchu.paymentservice.dto.request.walletHistory.WalletBalanceHistoryCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.walletHistory.WalletBalanceHistoryUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.walletHistory.WalletBalanceHistoryResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.walletHistory.WalletBalanceHistorySimpleResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.walletHistory.WalletBalanceStatsResponse;
import com.sidn.metruyenchu.paymentservice.entity.WalletBalanceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletBalanceHistoryMapper {
    // Chuyển đổi entity sang response đầy đủ
    WalletBalanceHistoryResponse toResponse(WalletBalanceHistory walletBalanceHistory);

    // Chuyển đổi entity sang response đơn giản
    WalletBalanceHistorySimpleResponse toSimpleResponse(WalletBalanceHistory walletBalanceHistory);

    // Chuyển đổi create request sang entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    WalletBalanceHistory toEntity(WalletBalanceHistoryCreateRequest request);

    // Chuyển đổi danh sách entity sang danh sách response đầy đủ
    List<WalletBalanceHistoryResponse> toResponses(List<WalletBalanceHistory> walletBalanceHistories);

    // Chuyển đổi danh sách entity sang danh sách response đơn giản
    List<WalletBalanceHistorySimpleResponse> toSimpleResponses(List<WalletBalanceHistory> walletBalanceHistories);

    // Cập nhật entity từ update request
    void updateEntity(@MappingTarget WalletBalanceHistory walletBalanceHistory,
                      WalletBalanceHistoryUpdateRequest request);

    // Mapping tùy chỉnh cho thống kê (có thể cần custom implementation)
    @Mapping(target = "currentBalance", source = "balanceAfter")
    @Mapping(target = "lastTransactionAt", source = "createdAt")
    @Mapping(target = "totalIncome", ignore = true) // Cần tính toán riêng
    @Mapping(target = "totalExpense", ignore = true) // Cần tính toán riêng
    @Mapping(target = "transactionCount", ignore = true) // Cần tính toán riêng
    WalletBalanceStatsResponse toStatsResponse(WalletBalanceHistory walletBalanceHistory);
}
