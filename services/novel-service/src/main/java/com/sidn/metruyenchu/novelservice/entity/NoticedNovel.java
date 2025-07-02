package com.sidn.metruyenchu.novelservice.entity;

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
public class NoticedNovel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    // Giả sử mỗi user chỉ được notice một novel một lần
    @Column(nullable = false)
    String userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "novel_id")
    Novel novel;

    @Column(nullable = false)
    LocalDateTime noticedAt;
}
