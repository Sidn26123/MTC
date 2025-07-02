package com.sidn.metruyenchu.feedbackservice.entity;
import com.sidn.metruyenchu.shared_library.enums.feedback.CommenterRole;
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
@Table(name = "report_comments",
       indexes = @Index(name = "idx_report_comment_id", columnList = "report_id"))
public class ReportComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    Report report;

    @Column(name = "commenter_id", nullable = false)
    String commenterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "commenter_role", nullable = false)
    CommenterRole commenterRole;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(name = "is_internal")
    Boolean isInternal = false;

    @Column(name = "created_at", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    LocalDateTime createdAt;
}
