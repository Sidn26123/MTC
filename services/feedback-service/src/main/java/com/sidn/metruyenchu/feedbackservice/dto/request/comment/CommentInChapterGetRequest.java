package com.sidn.metruyenchu.feedbackservice.dto.request.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentInChapterGetRequest {
    String chapterId;
    String chapterSlug;
    String chapterName;

    int page = 1;
    int size = 10;
}
