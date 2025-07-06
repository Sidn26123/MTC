package com.sidn.metruyenchu.novelservice.dto.request.draft;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DraftCreationRequest {
    String title;
    String content;
    String note;
    String publisher;
    String novelId;
    String chapterId;
}
