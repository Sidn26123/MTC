package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.Transactions;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionStatus;
import com.sidn.metruyenchu.shared_library.enums.payment.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Transactions, String> {
    Optional<Transactions> findByReferenceId(String id);
    
    Optional<Transactions> findByTransactionCode(String transactionCode);
    
    List<Transactions> findByUserIdOrderByCreatedAtDesc(String userId);

    Page<Transactions> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    List<Transactions> findByWalletIdOrderByCreatedAtDesc(String walletId);
    
    Page<Transactions> findByUserId(String userId, Pageable pageable);
    
    Page<Transactions> findByWalletId(String walletId, Pageable pageable);
    
    @Query("SELECT t FROM Transactions t WHERE " +
           "(:userId IS NULL OR t.userId = :userId) AND " +
           "(:walletId IS NULL OR t.wallet.id = :walletId) AND " +
           "(:type IS NULL OR t.type = :type) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:currencyId IS NULL OR t.currency.id = :currencyId) AND " +
           "(:fromDate IS NULL OR t.createdAt >= :fromDate) AND " +
           "(:toDate IS NULL OR t.createdAt <= :toDate)")
    Page<Transactions> searchTransactions(
            @Param("userId") String userId,
            @Param("walletId") String walletId,
            @Param("type") TransactionType type,
            @Param("status") TransactionStatus status,
            @Param("currencyId") String currencyId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable);
    
    @Query("SELECT SUM(t.amount) FROM Transactions t WHERE t.userId = :userId AND t.status = 'COMPLETED' AND t.type = :type")
    Optional<Long> getTotalAmountByUserAndType(@Param("userId") String userId, @Param("type") TransactionType type);
    
    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.userId = :userId AND t.status = :status")
    Long countByUserIdAndStatus(@Param("userId") String userId, @Param("status") TransactionStatus status);
}