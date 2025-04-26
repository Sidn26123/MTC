package com.sidn.metruyenchu.fileservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EnableJpaAuditing
public class ChapterContent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String chapterId;

    @Column(columnDefinition = "TEXT")
    String content;

    @Builder.Default
    int idx = 0;

    @Builder.Default
    Boolean isDeleted = false;
}
