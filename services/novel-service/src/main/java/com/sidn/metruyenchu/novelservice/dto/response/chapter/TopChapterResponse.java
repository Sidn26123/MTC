package com.sidn.metruyenchu.novelservice.dto.response.chapter;

import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.enums.ChapterState;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopChapterResponse {
    String id;
    String name;
    String publisher;
    Integer chapterIdx;
    Long viewCount;
    List<ChapterStatusResponse> chapterStatus;
    Integer amountToUnlock;
    ChapterState state;
    String stateLabel;
    NovelResponse novel;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime publishedAt;
}
