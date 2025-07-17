package com.sidn.metruyenchu.novelservice.dto.response.draft;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DraftResponse {
    String id;
    String title;
    String content;
    String note;
    String publisher;
    String novel;
    String chapter;
    String state;
    Boolean isConvertedToChapter;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
