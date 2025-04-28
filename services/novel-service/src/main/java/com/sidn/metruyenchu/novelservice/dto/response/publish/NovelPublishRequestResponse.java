package com.sidn.metruyenchu.novelservice.dto.response.publish;

import com.sidn.metruyenchu.novelservice.enums.PublishRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelPublishRequestResponse {
    String id;
    String message;
    String novelId;
    PublishRequestStatus status;
    String statusLabel;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
