package com.sidn.metruyenchu.novelservice.dto.response.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterListResponse {
    String id;
    String name;
    Integer chapterIdx;
}
