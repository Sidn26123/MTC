package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.ContentPurchase;
import com.sidn.metruyenchu.shared_library.enums.payment.ContentType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentPurchaseRepository extends JpaRepository<ContentPurchase, String> {
    Page<ContentPurchase> findByTransactionUserId(String userId, Pageable pageable);

    boolean existsByTransactionUserIdAndItemIdAndItemType(String userId, String itemId, ContentType itemType);

}
