package com.sidn.metruyenchu.novelservice.dto.response.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterListResponse {
    String id;
    String name;
    Integer chapterIdx;
    LocalDateTime publishedAt;;

}
