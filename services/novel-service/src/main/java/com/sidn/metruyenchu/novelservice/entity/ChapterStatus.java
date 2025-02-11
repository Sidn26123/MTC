package com.sidn.metruyenchu.novelservice.entity;

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
public class ChapterStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "chapterStatus", cascade = CascadeType.ALL)
    List<ChapterStatusDetail> chapterStatusDetails;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;
}
