package com.sidn.metruyenchu.fileservice.dto.response.policy;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyDocumentResponse {
    String id;
    String slug;
    String title;
    String content;
    Boolean isPublic;
    Integer version;
    String updatedBy;
    LocalDateTime updatedAt;
    LocalDateTime createdAt;
}
