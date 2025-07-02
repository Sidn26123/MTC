package com.sidn.metruyenchu.novelservice.dto.response.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterStatusResponse {
    String id;
    String name;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isDeleted;
}
