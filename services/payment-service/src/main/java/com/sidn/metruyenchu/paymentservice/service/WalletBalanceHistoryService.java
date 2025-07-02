package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.walletHistory.WalletBalanceHistoryCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.walletHistory.WalletBalanceHistorySimpleResponse;
import com.sidn.metruyenchu.paymentservice.entity.WalletBalanceHistory;
import com.sidn.metruyenchu.paymentservice.mapper.WalletBalanceHistoryMapper;
import com.sidn.metruyenchu.paymentservice.repository.WalletBalanceHistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WalletBalanceHistoryService {
    WalletBalanceHistoryRepository historyRepository;
    WalletBalanceHistoryMapper balanceHistoryMapper;
    public WalletBalanceHistorySimpleResponse recordBalanceChange(WalletBalanceHistoryCreateRequest request) {
        log.info("WalletBalanceHistoryService.recordBalanceChange: {}", request);

        WalletBalanceHistory history = balanceHistoryMapper.toEntity(request);

        history = historyRepository.save(history);

        return balanceHistoryMapper.toSimpleResponse(history);
    }
}
