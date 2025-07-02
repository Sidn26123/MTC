package com.sidn.metruyenchu.novelservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity thư viện của người dùng - lưu các truyện được người dùng đọc.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class BookShelf {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    ///Belongs to
    String userId;

    ///Tên gợi nhớ của bookshelf
    String name;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder.Default
    Boolean isDeleted = false;

    ///Nếu action thì bookshelf sẽ hiển thị cho user trong UI, ít nhất mỗi user phải có 1 bookshelf activating
    @Builder.Default
    Boolean isActive = true;

}
