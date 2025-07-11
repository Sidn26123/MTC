package com.sidn.metruyenchu.novelservice.dto.request.markednovel;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkedNovelUpdateRequest {
    String userId;
    String novelId;
    Integer markedAtChapter;
    Boolean isNoticed;
    Boolean isDeleted;
}
