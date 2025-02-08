package com.sidn.metruyenchu.novelservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainCharacterTraitResponse {
    String id;
    String name;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
    Boolean isActive;
}
