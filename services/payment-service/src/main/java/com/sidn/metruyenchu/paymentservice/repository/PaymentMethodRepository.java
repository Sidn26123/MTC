package com.sidn.metruyenchu.paymentservice.repository;

import com.sidn.metruyenchu.paymentservice.entity.PaymentMethod;
import com.sidn.metruyenchu.paymentservice.enums.PaymentMethodStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    Optional<PaymentMethod> findByCode(String code);
    boolean existsByCode(String code);

    Page<PaymentMethod> findAllByStatus(PaymentMethodStatus paymentMethodStatus, Pageable pageable);
}
