package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.paymentservice.exception.AppException;
import com.sidn.metruyenchu.paymentservice.exception.ErrorCode;
import com.sidn.metruyenchu.paymentservice.repository.CurrencyRepository;
import com.sidn.metruyenchu.paymentservice.repository.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CronJobService {
    CurrencyRepository currencyRepository;
    WalletRepository walletRepository;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void updatePromotionTicket(){
        Currency currency = getCurrency();
//        "f846a93f-af91-4ad9-aaf6-926ecb88595f"
        List<Wallet> wallets = walletRepository.findAllByCurrency(currency);
//        List<Wallet> wallets = walletRepository.findAll();
        log.info("Updating promotion ticket for {} wallets", wallets.size());
        for (Wallet wallet : wallets) {
            wallet.setBalance(BigDecimal.valueOf(2));
        }
        walletRepository.saveAll(wallets);

    }

    private Currency getCurrency() {
        String code = "XUK";
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));
    }

}
