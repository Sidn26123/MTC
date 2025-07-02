package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.paymentservice.enums.CurrencyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Optional<Currency> findByCode(String code);

    Page<Currency> findAllByCurrencyStatus(CurrencyStatus currencyStatus, Pageable pagable);

    List<Currency> findAllByCurrencyStatus(CurrencyStatus currencyStatus);
}
