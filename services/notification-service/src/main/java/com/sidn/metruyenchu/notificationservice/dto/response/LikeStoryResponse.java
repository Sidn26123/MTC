package com.sidn.metruyenchu.notificationservice.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeStoryResponse {
    String messageId;
    String message;
}
