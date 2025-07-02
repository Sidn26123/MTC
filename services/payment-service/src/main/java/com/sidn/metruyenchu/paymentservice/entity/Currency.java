package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.paymentservice.enums.CurrencyStatus;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    String id;

    @Column(nullable = false, unique = true)
    String code;

    @Column(nullable = false)
    Integer amount;

    @Column(nullable = false, length = 255)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    CurrencyStatus status = CurrencyStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    /**
     * Currency status enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CurrencyStatus currencyStatus;
}
