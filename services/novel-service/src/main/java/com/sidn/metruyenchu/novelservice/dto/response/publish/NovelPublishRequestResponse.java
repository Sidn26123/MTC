package com.sidn.metruyenchu.novelservice.dto.response.publish;

import com.sidn.metruyenchu.novelservice.dto.response.NovelResponse;
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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
