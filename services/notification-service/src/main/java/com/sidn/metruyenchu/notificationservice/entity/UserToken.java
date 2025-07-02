package com.sidn.metruyenchu.notificationservice.entity;

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
public class UserToken {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
    private String userId;

    @Column(length = 500)
    private String fcmToken;

    private Long lastUpdated;

    private boolean isActive;
}