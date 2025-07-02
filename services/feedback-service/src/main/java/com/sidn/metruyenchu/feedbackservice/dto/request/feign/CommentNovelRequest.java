package com.sidn.metruyenchu.feedbackservice.dto.request.feign;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentNovelRequest {
    String novelId;
    String chapterId;
    Integer chapterIdx;

}
