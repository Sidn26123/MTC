package com.sidn.metruyenchu.novelservice.dto.request.markednovel;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkedNovelDeleteRequest {
    String userId;
}
