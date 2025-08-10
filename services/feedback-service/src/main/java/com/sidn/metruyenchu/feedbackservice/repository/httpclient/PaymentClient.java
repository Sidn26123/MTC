package com.sidn.metruyenchu.feedbackservice.repository.httpclient;

import com.sidn.metruyenchu.feedbackservice.configuration.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@FeignClient(name = "payment-service", url = "http://localhost:8090/payment",
        configuration = {AuthenticationRequestInterceptor.class})
public interface PaymentClient {
    @GetMapping("/wallets/walletXuK")
    BigDecimal getWalletXuK();
}
