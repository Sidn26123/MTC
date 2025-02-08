package com.sidn.metruyenchu.novelservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class NovelStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 256)
    String name;


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;

    @Builder.Default
    Boolean isActive = true;

    @ManyToMany(mappedBy = "status", fetch = FetchType.LAZY)
    Set<Novel> novels = new HashSet<>();
}
