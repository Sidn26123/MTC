package com.sidn.metruyenchu.paymentservice.entity;
import com.sidn.metruyenchu.shared_library.enums.payment.vip.VipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "vip_memberships")
public class VipMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id", nullable = false)
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    VipPlan plan;

    @Column(name = "start_date", nullable = false)
    LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    VipStatus status = VipStatus.ACTIVE;

    @Column(name = "auto_renewal", nullable = false)
    @Builder.Default
    Boolean autoRenewal = false;

    @Column(name = "chapters_used_this_month", nullable = false)
    @Builder.Default
    Integer chaptersUsedThisMonth = 0;

    @Column(name = "last_chapter_reset")
    LocalDateTime lastChapterReset;

    @OneToOne
    @JoinColumn(name = "purchase_transaction_id")
    Transactions purchaseTransaction;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    LocalDateTime expiredAt;

    @OneToMany(mappedBy = "membership")
    List<VipChapterUsage> chapterUsages;


}