package com.sidn.metruyenchu.fileservice.entity;

import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EnableJpaAuditing
public class NovelFileManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String novelId;

    String chapterId;

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false, unique = true)
    FileManagement file;

    FileCategoryEnum category;

    @Builder.Default
    Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
