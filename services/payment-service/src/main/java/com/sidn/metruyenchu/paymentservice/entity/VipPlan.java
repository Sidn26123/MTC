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

import java.math.BigDecimal;
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
@Table(name = "vip_plans")
public class VipPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true)
    String name; // MONTHLY, QUARTERLY, YEARLY

    @Column(nullable = false)
    String displayName;

    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal price;

    @Column(name = "currency_id", nullable = false)
    String currencyId;

    @Column(name = "duration_days", nullable = false)
    Integer durationDays; // 30, 90, 365

    @Column(name = "free_chapters_per_month", nullable = false)
    Integer freeChaptersPerMonth; // 100

    @Column(name = "max_value_per_chapter", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    BigDecimal maxValuePerChapter = BigDecimal.valueOf(20); // Giới hạn giá trị tối đa cho mỗi chương VIP mà plan này có thể chi trả free

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    @Builder.Default
    BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    Boolean active = true;

    @Column(name = "sort_order")
    @Builder.Default
    Integer sortOrder = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "plan")
    List<VipMembership> memberships;
}