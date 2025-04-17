package com.sidn.metruyenchu.novelservice.dto.request.publish;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import com.sidn.metruyenchu.novelservice.enums.PublishRequestAction;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublishRequestActionLogCreationRequest extends BaseFilterRequest {
    String novelPublishRequestId;
    String actionBy;
    PublishRequestAction action;
}
