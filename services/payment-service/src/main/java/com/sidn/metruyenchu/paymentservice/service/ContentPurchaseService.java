package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.CheckUserPurchaseContentRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.ContentPurchaseCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.ContentPurchaseRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.contentPurchase.ContentPurchaseResponse;
import com.sidn.metruyenchu.paymentservice.entity.ContentPurchase;
import com.sidn.metruyenchu.paymentservice.entity.Transactions;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.paymentservice.enums.TransactionStatus;
import com.sidn.metruyenchu.paymentservice.enums.TransactionType;
import com.sidn.metruyenchu.paymentservice.enums.WalletStatus;
import com.sidn.metruyenchu.paymentservice.exception.AppException;
import com.sidn.metruyenchu.paymentservice.exception.ErrorCode;
import com.sidn.metruyenchu.paymentservice.mapper.ContentPurchaseMapper;
import com.sidn.metruyenchu.paymentservice.mapper.TransactionsMapper;
import com.sidn.metruyenchu.paymentservice.repository.ContentPurchaseRepository;
import com.sidn.metruyenchu.paymentservice.repository.TransactionsRepository;
import com.sidn.metruyenchu.paymentservice.repository.WalletRepository;
import com.sidn.metruyenchu.paymentservice.utils.PageUtils;
import com.sidn.metruyenchu.paymentservice.utils.TokenUtils;
import com.sidn.metruyenchu.shared_library.enums.payment.ContentType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContentPurchaseService {
    WalletRepository walletRepository;
    TransactionsRepository transactionsRepository;
    ContentPurchaseRepository contentPurchaseRepository;
    TransactionsMapper transactionsMapper;
    ContentPurchaseMapper contentPurchaseMapper;

    /**
     * Purchase content using wallet balance
     * @param contentPurchaseRequest the purchase request
     * @return the purchase response
     */
    @Transactional
    public ContentPurchaseResponse purchaseContent(ContentPurchaseRequest contentPurchaseRequest) {
        //Nếu không có userId trong request thì lấy từ context, xem như user thực hiện là user request

        String userId = contentPurchaseRequest.getUserId();
        if (userId == null){
            userId = TokenUtils.getUserIdFromContext();
        }

        // Validate request
        if (contentPurchaseRequest.getItemId() == null || contentPurchaseRequest.getItemType() == null) {
            throw new IllegalArgumentException("Item ID and type are required");
        }

        if (contentPurchaseRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        // Get user wallet
        Wallet wallet = walletRepository.findByUserIdAndCurrencyId(userId, contentPurchaseRequest.getCurrencyId())
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));

        if (wallet.getStatus() != WalletStatus.ACTIVE) {
            throw new IllegalStateException("Wallet is not active");
        }

        // Calculate final price
        BigDecimal finalPrice = contentPurchaseRequest.getPrice();
        if (contentPurchaseRequest.getDiscount() != null) {
            finalPrice = finalPrice.subtract(contentPurchaseRequest.getDiscount());
        }

        // Multiply by quantity
        finalPrice = finalPrice.multiply(BigDecimal.valueOf(contentPurchaseRequest.getQuantity()));

        // Check balance
        if (wallet.getBalance().compareTo(finalPrice) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        // Create transaction
        String transactionCode = "PUR-" + UUID.randomUUID().toString().substring(0, 8);
        Transactions transaction = Transactions.builder()
                .transactionCode(transactionCode)
                .userId(userId)
                .wallet(wallet)
                .type(TransactionType.PURCHASE)
                .amount(finalPrice.intValue())
                .currencyId(contentPurchaseRequest.getCurrencyId())
                .status(TransactionStatus.PENDING)
                .build();
        transaction = transactionsRepository.save(transaction);

        try {
            // Create content purchase record
            ContentPurchase contentPurchase = ContentPurchase.builder()
                    .transaction(transaction)
                    .itemType(contentPurchaseRequest.getItemType())
                    .itemId(contentPurchaseRequest.getItemId())
                    .price(contentPurchaseRequest.getPrice())
                    .finalPrice(finalPrice)
                    .currencyId(contentPurchaseRequest.getCurrencyId())
                    .quantity(contentPurchaseRequest.getQuantity())
                    .discount(contentPurchaseRequest.getDiscount())
                    .build();
            contentPurchase = contentPurchaseRepository.save(contentPurchase);

            // Update wallet balance
            wallet.setBalance(wallet.getBalance().subtract(finalPrice));
            walletRepository.save(wallet);

            // Complete transaction
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setCompletedAt(LocalDateTime.now());
            transactionsRepository.save(transaction);

            return contentPurchaseMapper.toResponse(contentPurchase);
        } catch (Exception e) {
            // Rollback transaction status in case of error
            transaction.setStatus(TransactionStatus.FAILED);
            transactionsRepository.save(transaction);
            throw e;
        }
    }

    /**
     * Get purchase by ID
     * @param id the purchase ID
     * @return the purchase response
     */
    public ContentPurchaseResponse getPurchaseById(String id) {
        return contentPurchaseRepository.findById(id)
                .map(contentPurchaseMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CONTENT_PURCHASE_NOT_FOUND));

    }

    /**
     * Get purchases by user ID
     * @param userId the user ID
     * @return list of purchase responses
     */
    public PageResponse<ContentPurchaseResponse> getPurchasesByUserId(String userId, BaseFilterRequest request) {
        Pageable pageable = PageUtils.from(request);

        Page<ContentPurchase> pageData = contentPurchaseRepository.findByTransactionUserId(
                userId,
                pageable
        );



        return PageUtils.toPageResponse(
                pageData,
                contentPurchaseMapper::toResponse,
                request.getPage(
        ));

    }

    public boolean hasPurchasedContent(String userId, String itemId, ContentType itemType) {
        // Kiểm tra xem người dùng đã mua nội dung này chưa
        return contentPurchaseRepository.existsByTransactionUserIdAndItemIdAndItemType(
                userId, itemId, itemType);

    }

    public boolean hasPurchasedContent(CheckUserPurchaseContentRequest request) {
        return contentPurchaseRepository.existsByTransactionUserIdAndItemIdAndItemType(
                request.getUserId(), request.getItemId(), request.getItemType());
    }
}
