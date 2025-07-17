package com.sidn.metruyenchu.fileservice.dto.request.policy;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyDocumentCreateRequest {
    String slug;

    String title;

    String content;

    @Builder.Default
    Boolean isPublic = true;
}
