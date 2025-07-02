package com.sidn.metruyenchu.feedbackservice.entity;

import com.sidn.metruyenchu.feedbackservice.enums.ReportHandleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ReportHandleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    Report report;
    @Column(nullable = false)
    String handledBy;
    String handlerNote;
    String description;

    @Enumerated(EnumType.STRING)
    ReportHandleStatus status;
    @Builder.Default
    boolean isDeleted = false;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(nullable = false)
    LocalDateTime completedAt;
    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @Column()
    LocalDateTime deletedAt;

}
