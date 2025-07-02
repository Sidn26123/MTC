package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.configurations.VNPAYConfig;
import com.sidn.metruyenchu.paymentservice.dto.request.walletHistory.WalletBalanceHistoryCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.PaymentResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.payment.VNPayResponse;
import com.sidn.metruyenchu.paymentservice.dto.response.wallet.WalletResponse;
import com.sidn.metruyenchu.paymentservice.entity.*;
import com.sidn.metruyenchu.paymentservice.entity.PaymentMethod;
import com.sidn.metruyenchu.paymentservice.enums.*;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.paymentservice.mapper.PaymentRequestsMapper;
import com.sidn.metruyenchu.paymentservice.mapper.TransactionsMapper;
import com.sidn.metruyenchu.paymentservice.mapper.WalletMapper;
import com.sidn.metruyenchu.paymentservice.repository.*;
import com.sidn.metruyenchu.paymentservice.utils.VNPayUtil;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WalletService {
    WalletRepository walletRepository;
    TransactionsRepository transactionsRepository;
    CurrencyRepository currenciesRepository;
    PaymentRequestRepository paymentRequestsRepository;
    PaymentMethodRepository paymentMethodsRepository;
    VNPAYConfig vnPayConfig;
    WalletMapper walletMapper;
    TransactionsMapper transactionsMapper;
    PaymentRequestsMapper paymentRequestsMapper;
    private final CurrencyService currencyService;
    private final WalletBalanceHistoryService walletBalanceHistoryService;

    /**
     * Find or create a wallet for a user
     * @param userId the user ID
     * @param currencyId the currency ID
     * @return the wallet
     */
    @Transactional
    public WalletResponse findOrCreateWallet(String userId, String currencyId) {
        Currency currency = currenciesRepository.findById(currencyId)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));

        Wallet wallet = walletRepository.findByUserIdAndCurrencyId(userId, currencyId)
                .orElseGet(() -> {
                    Wallet newWallet = Wallet.builder()
                            .userId(userId)
                            .currency(currency)
                            .balance(BigDecimal.ZERO)
                            .status(WalletStatus.ACTIVE)
                            .build();
                    return walletRepository.save(newWallet);
                });
        return walletMapper.toResponse(wallet);
    }

//    public Optional<WalletResponse> getWalletByUserIdAndCurrencyId(String userId, String currencyId) {
//        return walletRepository.findByUserIdAndCurrencyId(userId, currencyId)
//                .map(walletMapper::toResponse);
//    }

    public WalletResponse getWalletById(String id) {
        return walletRepository.findById(id)
                .map(walletMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }

    /**
     * Get wallet by user ID and currency ID
     * @param userId the user ID
     * @param currencyId the currency ID
     * @return the wallet response
     */
    public WalletResponse getWalletByUserIdAndCurrencyId(String userId, String currencyId) {
        Wallet wallet = walletRepository.findByUserIdAndCurrencyId(userId, currencyId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        return walletMapper.toResponse(wallet);
    }

    public List<WalletResponse> getAllWalletOfUser(String userId) {
        List<Wallet> wallets = walletRepository.findAllByUserId(userId);
        return walletMapper.toResponses(wallets);
    }

    /**
     * Initiate a wallet deposit using VNPAY
     * @param userId the user ID
     * @param currencyId the currency ID
     * @param amount the amount to deposit
     * @param request the HTTP request
     * @return the payment response
     */
    @Transactional
    public PaymentResponse initiateDeposit(String userId, String currencyId, BigDecimal amount, HttpServletRequest request) {
        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        // Validate currency
        Currency currency = currenciesRepository.findById(currencyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency ID"));

        if (currency.getStatus() == CurrencyStatus.INACTIVE) {
            throw new IllegalArgumentException("Currency is inactive");
        }

        // Get or create wallet
        Wallet wallet = walletRepository.findByUserIdAndCurrencyId(userId, currencyId)
                .orElseGet(() -> {
                    Wallet newWallet = Wallet.builder()
                            .userId(userId)
                            .currency(currency)
                            .balance(BigDecimal.ZERO)
                            .status(WalletStatus.ACTIVE)
                            .build();
                    return walletRepository.save(newWallet);
                });

        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            throw new IllegalStateException("Wallet is not active");
        }

        // Get VNPAY payment method
        PaymentMethod vnpayMethod = paymentMethodsRepository.findByCode("VNPAY")
                .orElseThrow(() -> new IllegalStateException("VNPAY payment method not found"));

        if (vnpayMethod.getStatus() != PaymentMethodStatus.ACTIVE) {
            throw new IllegalStateException("VNPAY payment method is not active");
        }

        // Create transaction record
        String transactionCode = "DEP-" + UUID.randomUUID().toString().substring(0, 8);
        Transactions transaction = Transactions.builder()
                .transactionCode(transactionCode)
                .userId(userId)
                .wallet(wallet)
                .type(TransactionType.DEPOSIT)
                .amount(amount.intValue())
                .currencyId(currencyId)
                .status(TransactionStatus.PENDING)
//                .transactionStatus(TransactionStatus.PENDING)
//                .transactionType(TransactionType.DEPOSIT)
                .build();
        transaction = transactionsRepository.save(transaction);

        // Create payment request
        String requestCode = "VNPAY-" + UUID.randomUUID().toString().substring(0, 8);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .requestCode(requestCode)
                .userId(userId)
                .paymentMethod(vnpayMethod)
                .amount(amount)
                .currencyId(currencyId)
                .callbackUrl("/api/v1/payments/vnpay/callback")
                .returnUrl("/api/v1/payments/vnpay/return")
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();
        paymentRequest = paymentRequestsRepository.save(paymentRequest);

        // Update transaction with reference ID
        transaction.setReferenceId(paymentRequest.getId());
        transactionsRepository.save(transaction);

        // Generate VNPAY payment URL
        VNPayResponse vnPayResponse = createVnPayPayment(request, amount, paymentRequest.getId());

        // Update payment request with payment URL
        paymentRequest.setPaymentUrl(vnPayResponse.getPaymentUrl());
        paymentRequestsRepository.save(paymentRequest);

        return PaymentResponse.builder()
                .requestId(paymentRequest.getId())
                .transactionId(transaction.getId())
                .paymentUrl(vnPayResponse.getPaymentUrl())
                .expiresAt(paymentRequest.getExpiresAt())
                .status("PENDING")
                .build();
    }

    /**
     * Process VNPAY payment callback
     * @param params the callback parameters
     * @return true if processed successfully
     */
    @Transactional
    public boolean processVnPayCallback(Map<String, String> params) {
        log.info("Processing VNPAY callback with params: {}", params);
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_TxnRef = params.get("vnp_TxnRef");

        // Find payment request using the reference
        Optional<PaymentRequest> paymentRequestOpt = paymentRequestsRepository.findById(vnp_TxnRef);
        if (paymentRequestOpt.isEmpty()) {
            log.error("Payment request not found: {}", vnp_TxnRef);
            return false;
        }

        PaymentRequest paymentRequest = paymentRequestOpt.get();

        // Find transaction
        Optional<Transactions> transactionOpt = transactionsRepository.findByReferenceId(paymentRequest.getId());
        if (transactionOpt.isEmpty()) {
            log.error("Transaction not found for payment request: {}", paymentRequest.getId());
            return false;
        }

        Transactions transaction = transactionOpt.get();

        // Validate transaction status
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            log.warn("Transaction already processed: {}", transaction.getId());
            return false;
        }

        // Process based on response code
        if ("00".equals(vnp_ResponseCode)) {
            // Success
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setCompletedAt(LocalDateTime.now());
            transactionsRepository.save(transaction);

            // Update payment request
            paymentRequest.setCompletedAt(LocalDateTime.now());
            paymentRequestsRepository.save(paymentRequest);

            // Update wallet balance
            Wallet wallet = transaction.getWallet();
            wallet.setBalance(wallet.getBalance().add(BigDecimal.valueOf(transaction.getAmount())));
            walletRepository.save(wallet);

//            walletBalanceHistoryService.recordBalanceChange(WalletBalanceHistoryCreateRequest.builder()
//                    .walletId(wallet.getId())
//                    .amount(BigDecimal.valueOf(transaction.getAmount()))
//                    .description("Deposit via VNPAY")
//                    .type(WalletBalanceHistoryType.DEPOSIT)
//                    .build());

            return true;
        } else {
            // Failed
            transaction.setStatus(TransactionStatus.FAILED);
            transactionsRepository.save(transaction);
            return false;
        }
    }

    /**
     * Create VNPAY payment
     * @param request the HTTP request
     * @param amount the amount
     * @param referenceId the reference ID
     * @return the VNPAY response
     */
    private VNPayResponse createVnPayPayment(HttpServletRequest request, BigDecimal amount, String referenceId) {
        long amountInVND = amount.multiply(BigDecimal.valueOf(100)).longValue();
        String bankCode = request.getParameter("bankCode");

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amountInVND));
        vnpParamsMap.put("vnp_TxnRef", referenceId);

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Build query URL
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VNPayResponse.builder()
                .code("00")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    public void initializeWalletForUser(String userId) {
        //currency list
        List<Currency> currencies = currenciesRepository.findAllByCurrencyStatus(CurrencyStatus.ACTIVE);

        for (Currency currency : currencies) {
            // Check if wallet already exists
            Optional<Wallet> existingWallet = walletRepository.findByUserIdAndCurrencyId(userId, currency.getId());
            if (existingWallet.isPresent()) {
                continue; // Wallet already exists, skip creation
            }

            // Create a new wallet for each active currency
            Wallet newWallet = Wallet.builder()
                    .userId(userId)
                    .currency(currency)
                    .balance(BigDecimal.ZERO)
                    .status(WalletStatus.ACTIVE)
                    .build();

            walletRepository.save(newWallet);
        }

        // Create a new wallet with zero balance

    }

    public boolean hasSufficientBalance(String userId, String currencyId, BigDecimal finalPrice) {
        Currency currency = currenciesRepository.findById(currencyId)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENCY_NOT_FOUND));

        return hasSufficientBalance(userId, currency, finalPrice);
    }

    public boolean hasSufficientBalance(String userId, Currency currency, BigDecimal finalPrice) {
        Wallet wallet = walletRepository.findByUserIdAndCurrencyId(userId, currency.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));

        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            throw new AppException(ErrorCode.WALLET_INACTIVE);
        }

        if (wallet.getBalance().compareTo(finalPrice) < 0) {
            return false;
        }

        return true;
    }


    public Wallet getWalletEntityByUserIdAndCurrency(String userId, Currency currency) {
        return walletRepository.findByUserIdAndCurrencyId(userId, currency.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
    }

    public Wallet getWalletEntityByUserIdAndCurrencyId(String userId, String currencyId) {
        Currency currency = currencyService.getCurrencyEntityById(currencyId);
        return walletRepository.findByUserIdAndCurrencyId(userId, currencyId)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
    }
}
