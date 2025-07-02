package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterNavigationRequest {
    String novelId;
    String chapterId;
    Integer currentChapterIdx;
    Float progress; // progress in the current chapter, from 0.0 to 1.0
    Boolean isNext; // true for next chapter, false for previous chapter
    Integer duration; // duration in seconds, used for reading log
}
