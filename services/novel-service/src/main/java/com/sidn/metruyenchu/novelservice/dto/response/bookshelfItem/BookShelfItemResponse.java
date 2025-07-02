package com.sidn.metruyenchu.novelservice.dto.response.bookshelfItem;

import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShelfItemResponse {
    String id;
//    String bookShelfId;
//    String novelId;
    NovelResponse novel;
    Integer currentChapterIdx;
    Boolean isDeleted;
}
