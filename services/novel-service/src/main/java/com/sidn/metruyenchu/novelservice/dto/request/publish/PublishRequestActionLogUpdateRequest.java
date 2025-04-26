package com.sidn.metruyenchu.novelservice.dto.request.publish;

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
public class PublishRequestActionLogUpdateRequest{
    String id;
    NovelPublishRequest novelPublishRequest;
    PublishRequestAction action;
    String actionBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
