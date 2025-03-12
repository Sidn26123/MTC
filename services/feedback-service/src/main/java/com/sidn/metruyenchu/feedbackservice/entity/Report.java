package com.sidn.metruyenchu.feedbackservice.entity;

import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import jakarta.persistence.*;
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
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String content;

    @Column(nullable = false)
    String reportedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    FeedbackType reportEntityType;

    @Column(nullable = false)
    String reportEntityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ReportStatus status;

    @OneToMany(mappedBy = "report")
    List<ReportHandleDetail> reportHandleDetails;


    @Builder.Default
    boolean isClosed = false;

    @Builder.Default
    boolean isDeleted = false;


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    LocalDateTime deletedAt;






}
