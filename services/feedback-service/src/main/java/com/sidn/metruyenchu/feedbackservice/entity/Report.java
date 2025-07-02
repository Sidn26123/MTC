package com.sidn.metruyenchu.feedbackservice.entity;

import com.sidn.metruyenchu.feedbackservice.configuration.JsonStringListConverter;
import com.sidn.metruyenchu.feedbackservice.enums.FeedbackType;
import com.sidn.metruyenchu.feedbackservice.enums.ReportStatus;
import com.sidn.metruyenchu.feedbackservice.enums.TargetType;
import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.Priority;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    String reporterId;

    @Enumerated(EnumType.STRING)
    ReportType reportType;

    @Enumerated(EnumType.STRING)
    TargetType targetType;

    @Column(name = "target_id")
    String targetId;

    String reportEntityId;

    String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    String description;

    @Convert(converter = JsonStringListConverter.class)
    @Column(name = "evidence_urls", columnDefinition = "text")
    private List<String> evidenceUrls;

    @Enumerated(EnumType.STRING)
    Priority priority = Priority.MEDIUM;

    @Enumerated(EnumType.STRING)
    ReportStatus status = ReportStatus.PENDING;

    @Column(name = "assigned_to")
    String assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_role")
    AssigneeRole assignedRole;

    @Column(name = "resolution_note", columnDefinition = "TEXT")
    String resolutionNote;


    @OneToMany(mappedBy = "report")
    List<ReportHandleDetail> reportHandleDetails;


    @Builder.Default
    boolean isClosed = false;

    @Builder.Default
    boolean isDeleted = false;


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    LocalDateTime deletedAt;


    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;




}
