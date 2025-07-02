package com.sidn.metruyenchu.novelservice.dto.response.novel;

import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterPublishCheckResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelCanPublishResponse {
    boolean canPublish;
    String message;
    List<ChapterPublishCheckResponse> errors;

}
