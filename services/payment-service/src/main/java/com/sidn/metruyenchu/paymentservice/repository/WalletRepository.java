package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.paymentservice.enums.WalletStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    Optional<Wallet> findByUserIdAndCurrencyId(String userId, String currencyId);
    Optional<Wallet> findByUserIdAndCurrencyIdAndStatus(String userId, String currencyId, WalletStatus walletStatus);
    List<Wallet> findAllByUserId(String userId);
    List<Wallet> findAllByUserIdAndStatus(String userId, WalletStatus walletStatus);
//    List<Wallet> findAllByCurrencyId(String currencyId);
    List<Wallet> findAllByCurrency(Currency currency);
    boolean existsByUserId(String userId);
}