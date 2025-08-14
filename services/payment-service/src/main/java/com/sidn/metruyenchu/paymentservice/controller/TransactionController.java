package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsUpdateRequest;
import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionSearchRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.transactions.TransactionsCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.transactions.TransactionsResponse;
import com.sidn.metruyenchu.paymentservice.service.TransactionsService;
import com.sidn.metruyenchu.shared_library.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Validated
public class TransactionController {
    TransactionsService transactionsService;

    /**
     * Create a new transaction
     * @param request the transaction creation request
     * @return the created transaction response
     */
    @PostMapping
    public ApiResponse<TransactionsResponse> createTransaction(
            @Valid @RequestBody TransactionsCreateRequest request) {
        return ApiResponse.<TransactionsResponse>builder()
                .result(transactionsService.createTransaction(request))
                .build();
    }

    /**
     * Get transaction by ID
     * @param id the transaction ID
     * @return the transaction response
     */
    @GetMapping("/{id}")
    public ApiResponse<TransactionsResponse> getTransaction(@PathVariable String id) {
        return ApiResponse.<TransactionsResponse>builder()
                .result(transactionsService.getTransactionById(id))
                .build();
    }

    /**
     * Get transaction by transaction code
     * @param transactionCode the transaction code
     * @return the transaction response
     */
    @GetMapping("/code/{transactionCode}")
    public ApiResponse<TransactionsResponse> getTransactionByCode(@PathVariable String transactionCode) {
        return ApiResponse.<TransactionsResponse>builder()
                .result(transactionsService.getTransactionByCode(transactionCode))
                .build();
    }

    /**
     * Get transaction by reference ID
     * @param referenceId the reference ID
     * @return the transaction response
     */
    @GetMapping("/reference/{referenceId}")
    public ApiResponse<TransactionsResponse> getTransactionByReferenceId(@PathVariable String referenceId) {
        return ApiResponse.<TransactionsResponse>builder()
                .result(transactionsService.getTransactionByReferenceId(referenceId))
                .build();
    }

    /**
     * Get all transactions for a user
     * @param userId the user ID
     * @return the list of transaction responses
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<PageResponse<TransactionsResponse>> getUserTransactions(
            @PathVariable String userId,
            @ModelAttribute BaseFilterRequest request
    ) {
//        BaseFilterRequest request = BaseFilterRequest.builder()
//                .page(1)
//                .size(10)
//                .build();
        log.info("A");
        log.info("Fetching transactions for user: {}", request);
        log.info("User ID: {}", userId);
        return ApiResponse.<PageResponse<TransactionsResponse>>builder()
                .result(transactionsService.getUserTransactions(userId, request))
                .build();
    }

    /**
     * Search transactions with filters and pagination
     * @param request the search request with filters
     * @return the paginated transaction responses
     */
    @PostMapping("/search")
    public ApiResponse<PageResponse<TransactionsResponse>> searchTransactions(
            @Valid @RequestBody TransactionSearchRequest request) {
        return ApiResponse.<PageResponse<TransactionsResponse>>builder()
                .result(transactionsService.searchTransactions(request))
                .build();
    }

    /**
     * Update transaction status
     * @param id the transaction ID
     * @param request the status update request
     * @return the updated transaction response
     */
    @PutMapping("/{id}/status")
    public ApiResponse<TransactionsResponse> updateTransactionStatus(
            @PathVariable String id,
            @Valid @RequestBody TransactionsUpdateRequest request) {
        return ApiResponse.<TransactionsResponse>builder()
                .result(transactionsService.updateTransactionStatus(id, request))
                .build();
    }

    /**
     * Cancel a pending transaction
     * @param id the transaction ID
     * @return success message
     */
    @PutMapping("/{id}/cancel")
    public ApiResponse<String> cancelTransaction(@PathVariable String id) {
        transactionsService.cancelTransaction(id);
        return ApiResponse.<String>builder()
                .result("Transaction cancelled successfully")
                .build();
    }

    /**
     * Get user transaction statistics
     * @param userId the user ID
     * @return the transaction statistics
     */
    @GetMapping("/user/{userId}/statistics")
    public ApiResponse<Map<String, Object>> getUserTransactionStatistics(@PathVariable String userId) {
        return ApiResponse.<Map<String, Object>>builder()
                .result(transactionsService.getUserTransactionStatistics(userId))
                .build();
    }

    @GetMapping("/year/{year}")
    public List<BigDecimal> getUserSpending(
            @PathVariable int year
    ) {
        return transactionsService.getMonthlyUserSpending( year);
    }
}

