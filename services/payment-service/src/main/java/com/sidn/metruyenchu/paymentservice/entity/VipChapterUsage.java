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

/**
 * Entity dai dien cho viec su dung luot doc chuong VIP tu VIP membership
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "vip_chapter_usages")
public class VipChapterUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", nullable = false)
    VipMembership membership;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "story_id", nullable = false)
    String storyId;

    @Column(name = "chapter_id", nullable = false)
    String chapterId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime readAt;

    @Column(name = "year_month", nullable = false)
    String yearMonth; // Format: "2025-06" for indexing
}
