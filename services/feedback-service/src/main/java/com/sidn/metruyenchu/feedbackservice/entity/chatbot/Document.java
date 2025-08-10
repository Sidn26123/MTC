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
@Entity
@Table(name = "rag_app_document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;

    String uploadedBy;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentChunk> chunks;

    @PrePersist
    public void prePersist() {
        uploadedAt = LocalDateTime.now();
    }
}
