package com.sidn.metruyenchu.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class LevelThresholds {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    Integer level;
    Integer experiencePoint;
}
