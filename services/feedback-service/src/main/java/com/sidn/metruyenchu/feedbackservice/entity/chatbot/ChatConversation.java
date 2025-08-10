package com.sidn.metruyenchu.feedbackservice.entity.chatbot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity@Table(name = "rag_app_chatconversation")
public class ChatConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    String userId;

    @Lob
    private String question;

    @Lob
    private String answer;

    @Lob
    @Column(name = "context_used")
    private String contextUsed;

    @Column(name = "response_time")
    private Double responseTime = 0.0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "is_flagged")
    private Boolean isFlagged = false;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
