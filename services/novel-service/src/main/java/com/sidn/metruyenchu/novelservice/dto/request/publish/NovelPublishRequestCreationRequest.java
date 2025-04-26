package com.sidn.metruyenchu.novelservice.dto.request.publish;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelPublishRequestCreationRequest extends BaseFilterRequest {
    String novelId;
    String message;
    String requestedBy;
}
