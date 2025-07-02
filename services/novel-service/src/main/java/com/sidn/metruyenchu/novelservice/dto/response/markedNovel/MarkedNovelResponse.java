package com.sidn.metruyenchu.novelservice.dto.response.markedNovel;

import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkedNovelResponse {
    String id;
    String userId;
    NovelResponse novel;
    Integer markedAtChapter;

    Boolean isNoticed;
    Boolean isDeleted;
}
