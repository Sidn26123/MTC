package com.sidn.metruyenchu.novelservice.dto.response.publish;

import com.sidn.metruyenchu.novelservice.entity.NovelPublishRequest;
import com.sidn.metruyenchu.novelservice.enums.PublishRequestAction;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublishRequestActionLogResponse {
    String id;
    NovelPublishRequestResponse novelPublishRequestResponse;
    PublishRequestAction action;
    String actionLabel;
    String actionBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
