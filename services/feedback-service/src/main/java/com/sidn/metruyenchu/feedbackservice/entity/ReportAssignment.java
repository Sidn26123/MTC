package com.sidn.metruyenchu.feedbackservice.entity;

import com.sidn.metruyenchu.shared_library.enums.feedback.AssigneeRole;
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
@Table(name = "report_assignments",
       uniqueConstraints = @UniqueConstraint(columnNames = {"report_id", "assignee_id"}))
public class ReportAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    Report report;

    @Column(name = "assignee_id", nullable = false)
    String assigneeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignee_role", nullable = false)
    AssigneeRole assigneeRole;

    @Builder.Default
    @Column(name = "is_primary")
    Boolean isPrimary = false;

    @Column(name = "assigned_at", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    LocalDateTime assignedAt;
}
