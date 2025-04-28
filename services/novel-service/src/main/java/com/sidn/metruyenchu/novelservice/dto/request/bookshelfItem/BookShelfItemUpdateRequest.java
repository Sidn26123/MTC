package com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShelfItemUpdateRequest {
    String bookShelfId;
    String novelId;
    Integer currentChapterIdx;
    Boolean isDeleted;
}
