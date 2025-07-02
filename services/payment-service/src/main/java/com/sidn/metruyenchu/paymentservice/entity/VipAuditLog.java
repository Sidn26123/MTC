package com.sidn.metruyenchu.paymentservice.entity;

import com.sidn.metruyenchu.shared_library.enums.payment.vip.UserCouponStatus;
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

@Entity
@Table(name = "vip_audit_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipAuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "action", nullable = false)
    private String action; // PURCHASE_VIP, READ_CHAPTER, USE_COUPON, etc.
    
    @Column(name = "entity_type", nullable = false)
    private String entityType; // VIP_MEMBERSHIP, COUPON, CHAPTER
    
    @Column(name = "entity_id")
    private String entityId;
    
    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;
    
    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
