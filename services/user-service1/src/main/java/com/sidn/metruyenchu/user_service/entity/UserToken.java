package com.sidn.metruyenchu.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_tokens")
public class UserToken {
    @Id
    private String userId;

    @Column(length = 500)
    private String fcmToken;

    private Long lastUpdated;
}