package com.sidn.metruyenchu.novelservice.dto.request.publish;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.enums.PublishRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelPublishRequestFilter extends BaseFilterRequest {
    String novelId;
    PublishRequestStatus status;
    String requestedBy; // Người yêu cầu xuất bản
}
