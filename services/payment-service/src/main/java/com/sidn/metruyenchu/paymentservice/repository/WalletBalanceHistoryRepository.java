package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.WalletBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletBalanceHistoryRepository extends JpaRepository<WalletBalanceHistory, String> {
}
