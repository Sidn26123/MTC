package com.sidn.metruyenchu.fileservice.dto.request.policy;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyDocumentUpdateRequest {
    String title;
    String content;
    Boolean isPublic;
}
