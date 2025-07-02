package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.paymentservice.enums.WalletStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Wallet Entity representing user's wallet information.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "wallet",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "currency"})
)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", insertable = false, updatable = false)
    Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    WalletStatus status = WalletStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "wallet")
    List<Transactions> transactions;

//    /**
//     * Wallet status enum.
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    WalletStatus walletStatus;
}