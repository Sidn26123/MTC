package com.sidn.metruyenchu.feedbackservice.entity;
import com.sidn.metruyenchu.shared_library.enums.feedback.ActorRole;
import com.sidn.metruyenchu.shared_library.enums.feedback.ReportActionType;
import com.sidn.metruyenchu.shared_library.enums.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "report_actions",
       indexes = @Index(name = "idx_report_id", columnList = "report_id"))
public class ReportAction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    Report report;

    @Column(name = "actor_id", nullable = false)
    String actorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_role", nullable = false)
    ActorRole actorRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    ReportActionType actionType;

    @Column(name = "old_value")
    String oldValue;

    @Column(name = "new_value")
    String newValue;

    @Column(columnDefinition = "TEXT")
    String note;

    @Column(name = "created_at", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    LocalDateTime createdAt;
}
