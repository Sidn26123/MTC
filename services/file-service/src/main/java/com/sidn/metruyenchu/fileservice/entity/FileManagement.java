package com.sidn.metruyenchu.fileservice.entity;

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
public class FileManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String fileName;
    String displayName;
    String path;
//    String path;
    String fileExtension;
    String contentType;
    String relativePath;
    long size;
    String ownerId;
    String md5Checksum;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    NovelFileManagement novelFileManagement;  // Nếu muốn có tham chiếu ngược

    @Builder.Default
    Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
