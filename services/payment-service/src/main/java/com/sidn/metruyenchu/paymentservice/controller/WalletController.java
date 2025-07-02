package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.PaymentResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.wallet.WalletResponse;
import com.sidn.metruyenchu.paymentservice.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WalletController {
    WalletService walletService;

    /**
     * Get wallet by ID
     * @param id the wallet ID
     * @return the wallet response
     */
    @GetMapping("/{id}")
    public ApiResponse<WalletResponse> getWalletById(@PathVariable String id) {
        return ApiResponse.<WalletResponse>builder()
                .result(walletService.getWalletById(id))
                .build();
    }

    /**
     * Get or create wallet
     * @param userId the user ID
     * @param currencyId the currency ID
     * @return the wallet response
     */
    @GetMapping
    public ApiResponse<WalletResponse> getOrCreateWallet(
            @RequestParam String userId,
            @RequestParam String currencyId) {
        WalletResponse wallet = walletService.findOrCreateWallet(userId, currencyId);
        return ApiResponse.<WalletResponse>builder()
                .result(wallet)
                .build();
    }

    /**
     * Initiate a deposit to wallet
     * @param userId the user ID
     * @param currencyId the currency ID
     * @param amount the amount to deposit
     * @param request the HTTP request
     * @return the payment response
     */
    @PostMapping("/deposit")
    public ApiResponse<PaymentResponse> deposit(
            @RequestParam String userId,
            @RequestParam String currencyId,
            @RequestParam BigDecimal amount,
            HttpServletRequest request) {
        PaymentResponse response = walletService.initiateDeposit(userId, currencyId, amount, request);
        return ApiResponse.<PaymentResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/user/currency")
    public ApiResponse<WalletResponse> getWalletByUserAndCurrency(
            @RequestParam String userId,
            @RequestParam String currencyId) {
        return ApiResponse.<WalletResponse>builder()
                .result(walletService.getWalletByUserIdAndCurrencyId(userId, currencyId))
                .build();
    }

    /**
     * Get all wallets of a user
     * Example: GET /wallets/user/abc123
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<WalletResponse>> getAllWalletsOfUser(@PathVariable String userId) {
        return ApiResponse.<List<WalletResponse>>builder()
                .result(walletService.getAllWalletOfUser(userId))
                .build();
    }

    /**
     * Initialize a wallet for a user
     *
     */
    @PostMapping("/user/{userId}/init")
    public ApiResponse<Void> initializeWalletForUser(@PathVariable String userId) {
        walletService.initializeWalletForUser(userId);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }

}
