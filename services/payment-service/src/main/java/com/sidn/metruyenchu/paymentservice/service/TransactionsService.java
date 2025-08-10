package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionSearchRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.transactions.TransactionsResponse;
import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.entity.Transactions;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
import com.sidn.metruyenchu.paymentservice.mapper.TransactionsMapper;
import com.sidn.metruyenchu.paymentservice.repository.TransactionsRepository;
import com.sidn.metruyenchu.paymentservice.repository.WalletRepository;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus;
import com.sidn.metruyenchu.shared_library.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TransactionsService {
//    TransactionsRepository transactionsRepository;
//    TransactionsMapper transactionsMapper;
//
//    public Transactions createTransaction(TransactionsCreateRequest request) {
//        // Create a new transaction record
//        Transactions transaction = transactionsMapper.toEntity(request);
//        transaction.setTransactionCode(generateTransactionCode(request.getType()));
//
//        // Save the transaction to the database (assuming there's a repository for Transactions)
//         transactionsRepository.save(transaction);
//
//        return transaction;
//    }
//
//
//    public String generateTransactionCode() {
//        // Generate a unique transaction code
//        // This could be a UUID, a sequence number, or any other unique identifier
//        return "TXN-" + System.currentTimeMillis();
//    }
//
//    public String generateTransactionCode(TransactionType transactionType) {
//        // Generate a unique transaction code based on the transaction type
//        return transactionType.name() + "-" + System.currentTimeMillis() + "." + (int) (Math.random() * 1000);
//    }
    TransactionsRepository transactionsRepository;
    TransactionsMapper transactionsMapper;
    WalletRepository walletRepository; // Assumed to exist

    public TransactionsResponse createTransaction(TransactionsCreateRequest request) {
        log.info("Creating transaction for user: {}, amount: {}", request.getUserId(), request.getAmount());

        // Validate wallet exists and belongs to user
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));

        if (!wallet.getUserId().equals(request.getUserId())) {
            throw new AppException(ErrorCode.WALLET_NOT_BELONG_TO_USER);
        }

        // Create transaction
        Transactions transaction = transactionsMapper.toEntity(request);
        transaction.setWallet(wallet);
        transaction.setCurrency(wallet.getCurrency());
        transaction.setTransactionCode(generateTransactionCode(request.getType()));
        transaction.setStatus(TransactionStatus.PENDING);

        // For certain transaction types, check wallet balance
        if (request.getType() == TransactionType.PURCHASE || request.getType() == TransactionType.WITHDRAW) {
            validateSufficientBalance(wallet, request.getAmount());
        }

        Transactions savedTransaction = transactionsRepository.save(transaction);
        log.info("Transaction created successfully with code: {}", savedTransaction.getTransactionCode());

        return transactionsMapper.toResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public TransactionsResponse getTransactionById(String id) {
        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        return transactionsMapper.toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public TransactionsResponse getTransactionByCode(String transactionCode) {
        Transactions transaction = transactionsRepository.findByTransactionCode(transactionCode)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        return transactionsMapper.toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public TransactionsResponse getTransactionByReferenceId(String referenceId) {
        Transactions transaction = transactionsRepository.findByReferenceId(referenceId)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        return transactionsMapper.toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public PageResponse<TransactionsResponse> getUserTransactions(String userId, BaseFilterRequest request) {
        log.info("Fetching transactions for user: {}", request);
        Pageable pageable = PageUtils.from(request);
        Page<Transactions> transactions = transactionsRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return PageUtils.toPageResponse(
                transactions,
                transactionsMapper::toResponse,
                pageable.getPageNumber()+ 1
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<TransactionsResponse> searchTransactions(TransactionSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.Direction.fromString(request.getSortDirection()),
                request.getSortBy()
        );

        Page<Transactions> transactions = transactionsRepository.searchTransactions(
                request.getUserId(),
                request.getWalletId(),
                request.getType(),
                request.getStatus(),
                request.getCurrencyId(),
                request.getFromDate(),
                request.getToDate(),
                pageable
        );

        return PageUtils.toPageResponse(
                transactions,
                transactionsMapper::toResponse,
                request.getPage()
        );
    }

    public TransactionsResponse updateTransactionStatus(String id, TransactionsUpdateRequest request) {
        log.info("Updating transaction status: {} to {}", id, request.getStatus());

        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        // Validate status transition
        validateStatusTransition(transaction.getStatus(), request.getStatus());

        TransactionStatus oldStatus = transaction.getStatus();
        transaction.setStatus(request.getStatus());

        // Set completion time if transaction is completed
        if (request.getStatus() == TransactionStatus.COMPLETED) {
            transaction.setCompletedAt(LocalDateTime.now());
            // Process wallet balance update
            processWalletBalanceUpdate(transaction);
        } else if (request.getStatus() == TransactionStatus.FAILED && oldStatus == TransactionStatus.PENDING) {
            // Handle failed transaction logic if needed
            log.warn("Transaction {} failed", transaction.getTransactionCode());
        }

        Transactions updatedTransaction = transactionsRepository.save(transaction);
        log.info("Transaction status updated successfully: {}", updatedTransaction.getTransactionCode());

        return transactionsMapper.toResponse(updatedTransaction);
    }

    public void cancelTransaction(String id) {
        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Chỉ có thể hủy giao dịch đang chờ xử lý");
        }

        transaction.setStatus(TransactionStatus.CANCELLED);
        transactionsRepository.save(transaction);

        log.info("Transaction cancelled: {}", transaction.getTransactionCode());
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getUserTransactionStatistics(String userId) {
        Map<String, Object> stats = new HashMap<>();

        // Total transactions by status
        for (TransactionStatus status : TransactionStatus.values()) {
            Long count = transactionsRepository.countByUserIdAndStatus(userId, status);
            stats.put("total_" + status.name().toLowerCase(), count);
        }

        // Total amounts by transaction type
        for (TransactionType type : TransactionType.values()) {
            Optional<Long> totalAmount = transactionsRepository.getTotalAmountByUserAndType(userId, type);
            stats.put("total_amount_" + type.name().toLowerCase(), totalAmount.orElse(0L));
        }

        return stats;
    }

    private void validateSufficientBalance(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Số dư không đủ để thực hiện giao dịch");
        }
    }

    private void validateSufficientBalance(Wallet wallet, Integer amount) {
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);

        if (wallet.getBalance().compareTo(amountDecimal) < 0) {
            throw new RuntimeException("Số dư không đủ để thực hiện giao dịch");
        }
    }

    private void validateStatusTransition(TransactionStatus currentStatus, TransactionStatus newStatus) {
        // Define valid status transitions
        Set<TransactionStatus> validTransitions = new HashSet<>();

        switch (currentStatus) {
            case PENDING:
                validTransitions.addAll(Arrays.asList(TransactionStatus.COMPLETED, TransactionStatus.FAILED, TransactionStatus.CANCELLED));
                break;
            case COMPLETED:
                // Completed transactions generally cannot be changed, except for refunds
                validTransitions.add(TransactionStatus.REFUNDED);
                break;
            case FAILED:
            case CANCELLED:
            case REFUNDED:
                // Terminal states - no transitions allowed
                break;
        }

        if (!validTransitions.contains(newStatus)) {
            throw new RuntimeException("Không thể chuyển trạng thái từ " + currentStatus + " sang " + newStatus);
        }
    }

    private void processWalletBalanceUpdate(Transactions transaction) {
        Wallet wallet = transaction.getWallet();
        BigDecimal amount = transaction.getAmount();

        switch (transaction.getType()) {
            case DEPOSIT:
                wallet.setBalance(wallet.getBalance().add(amount));
                break;
            case PURCHASE:
            case WITHDRAW:
                wallet.setBalance(wallet.getBalance().subtract(amount));
                break;
            case REFUND:
                wallet.setBalance(wallet.getBalance().add(amount));
                break;
        }

        walletRepository.save(wallet);
        log.info("Wallet balance updated for transaction: {}", transaction.getTransactionCode());
    }

    public String generateTransactionCode(TransactionType transactionType) {
        String prefix = switch (transactionType) {
            case DEPOSIT -> "DEP";
            case PURCHASE -> "PUR";
            case WITHDRAW -> "WTH";
            case REFUND -> "REF";
            default -> "TXN";
        };

        return prefix + "-" + System.currentTimeMillis() + "-" + String.format("%04d", (int) (Math.random() * 10000));
    }


}
