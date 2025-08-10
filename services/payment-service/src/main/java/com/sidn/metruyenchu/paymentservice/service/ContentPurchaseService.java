package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.paymentservice.dto.PageResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.contentPurchase.*;
import com.sidn.metruyenchu.paymentservice.dto.response.contentPurchase.ContentPurchaseResponse;
import com.sidn.metruyenchu.paymentservice.entity.ContentPurchase;
import com.sidn.metruyenchu.paymentservice.entity.Currency;
import com.sidn.metruyenchu.paymentservice.entity.Transactions;
import com.sidn.metruyenchu.paymentservice.entity.Wallet;
import com.sidn.metruyenchu.paymentservice.repository.http.NovelClient;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
import com.sidn.metruyenchu.paymentservice.enums.WalletStatus;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
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
import java.util.ArrayList;
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
    CurrencyService currencyService;

//    NovelClient novelClient;

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

        boolean hasPur =  hasPurchasedContent(CheckUserPurchaseContentRequest.builder()
                .userId(userId)
                .itemId(contentPurchaseRequest.getItemId())
                .itemType(contentPurchaseRequest.getItemType())
                .build());

        if (hasPur) {
            throw new AppException(ErrorCode.CONTENT_PURCHASE_ALREADY_EXISTS);
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

        Currency currency = currencyService.getCurrencyEntityById(contentPurchaseRequest.getCurrencyId());
        // Create transaction
        String transactionCode = "PUR-" + UUID.randomUUID().toString().substring(0, 8);
        Transactions transaction = Transactions.builder()
                .transactionCode(transactionCode)
                .userId(userId)
                .wallet(wallet)
                .type(TransactionType.PURCHASE)
                .amount(BigDecimal.valueOf(finalPrice.intValue()))
                .currency(currency)
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

    @Transactional
    public ContentPurchaseResponse purchaseContents(BulkChapterPurchaseRequest request) {
//        String userId = request.getUserId();
//        if (userId == null) {
//            userId = TokenUtils.getUserIdFromContext();
//        }
//
//        // Validate request
//        if (request.getChapterIds() == null || request.getChapterIds().isEmpty()) {
//            throw new IllegalArgumentException("Chapter IDs are required");
//        }
//
//        // Get user wallet
//        Wallet wallet = walletRepository.findByUserIdAndCurrencyId(userId, request.getCurrencyId())
//                .orElseThrow(() -> new IllegalStateException("Wallet not found"));
//
//        if (wallet.getStatus() != WalletStatus.ACTIVE) {
//            throw new IllegalStateException("Wallet is not active");
//        }
//
//        // Check if user already purchased any of these chapters
//        List<String> alreadyPurchased = new ArrayList<>();
//        for (String chapterId : request.getChapterIds()) {
//            boolean hasPur = hasPurchasedContent(CheckUserPurchaseContentRequest.builder()
//                    .userId(userId)
//                    .itemId(chapterId)
//                    .itemType(ContentType.CHAPTER)
//                    .build());
//            if (hasPur) {
//                alreadyPurchased.add(chapterId);
//            }
//        }
//
//        if (!alreadyPurchased.isEmpty()) {
//            throw new AppException(ErrorCode.CONTENT_PURCHASE_ALREADY_EXISTS,
//                    "Already purchased chapters: " + String.join(", ", alreadyPurchased));
//        }
//
//        // Calculate total price
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        for (String chapterId : request.getChapterIds()) {
//            // Lấy giá của từng chapter (có thể từ database hoặc service khác)
//            BigDecimal chapterPrice = getChapterPrice(chapterId);
//            totalPrice = totalPrice.add(chapterPrice);
//        }
//
//        // Apply bulk discount if any
//        if (request.getDiscount() != null) {
//            totalPrice = totalPrice.subtract(request.getDiscount());
//        }
//
//        // Check balance
//        if (wallet.getBalance().compareTo(totalPrice) < 0) {
//            throw new IllegalStateException("Insufficient balance");
//        }
//
//        Currency currency = currencyService.getCurrencyEntityById(request.getCurrencyId());
//
//        // Create main transaction
//        String transactionCode = "BULK-PUR-" + UUID.randomUUID().toString().substring(0, 8);
//        Transactions transaction = Transactions.builder()
//                .transactionCode(transactionCode)
//                .userId(userId)
//                .wallet(wallet)
//                .type(TransactionType.BULK_PURCHASE)
//                .amount(totalPrice)
//                .currency(currency)
//                .status(TransactionStatus.PENDING)
//                .build();
//        transaction = transactionsRepository.save(transaction);
//
//        try {
//            // Create content purchase records for each chapter
//            List<ContentPurchase> purchases = new ArrayList<>();
//            for (String chapterId : request.getChapterIds()) {
//                BigDecimal chapterPrice = getChapterPrice(chapterId);
//
//                ContentPurchase contentPurchase = ContentPurchase.builder()
//                        .transaction(transaction)
//                        .itemType(ContentType.CHAPTER)
//                        .itemId(chapterId)
//                        .price(chapterPrice)
//                        .finalPrice(chapterPrice)
//                        .currencyId(request.getCurrencyId())
//                        .quantity(1)
//                        .build();
//                purchases.add(contentPurchase);
//            }
//
//            // Save all purchases
//            contentPurchaseRepository.saveAll(purchases);
//
//            // Update wallet balance
//            wallet.setBalance(wallet.getBalance().subtract(totalPrice));
//            walletRepository.save(wallet);
//
//            // Complete transaction
//            transaction.setStatus(TransactionStatus.COMPLETED);
//            transaction.setCompletedAt(LocalDateTime.now());
//            transactionsRepository.save(transaction);
//
//            // Return response with summary
//            return ContentPurchaseResponse.builder()
//                    .transactionCode(transactionCode)
//                    .totalAmount(totalPrice)
//                    .purchasedItemCount(request.getChapterIds().size())
//                    .status("SUCCESS")
//                    .message("Successfully purchased " + request.getChapterIds().size() + " chapters")
//                    .build();
//
//        } catch (Exception e) {
//            // Rollback transaction status
//            transaction.setStatus(TransactionStatus.FAILED);
//            transactionsRepository.save(transaction);
//            throw e;
//        }
        return null;
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
        if (request.getUserId() == null){
            request.setUserId(TokenUtils.getUserIdFromContext());
        }
        return contentPurchaseRepository.existsByTransactionUserIdAndItemIdAndItemType(
                request.getUserId(), request.getItemId(), request.getItemType());
    }

    public boolean hasPurchasedNovelOrChapter(CheckUserCanReadContentRequest request) {
        if (request.getUserId() == null) {
            request.setUserId(TokenUtils.getUserIdFromContext());
        }
        log.info("Checking if user {} has purchased novel {} or chapter {}",
                request.getUserId(), request.getNovelId(), request.getChapterId());
        // Kiểm tra xem người dùng đã mua truyện này chưa
        boolean hasPurchasedNovel = contentPurchaseRepository.existsByTransactionUserIdAndItemIdAndItemType(
                request.getUserId(), request.getNovelId(), ContentType.NOVEL);

        if (hasPurchasedNovel) {
            return true;
        }

        boolean hasPurchasedChapter = contentPurchaseRepository.existsByTransactionUserIdAndItemIdAndItemType(
                request.getUserId(), request.getChapterId(), ContentType.CHAPTER);

        if (hasPurchasedChapter) {
            return true;
        }

        return false;
    }

}
