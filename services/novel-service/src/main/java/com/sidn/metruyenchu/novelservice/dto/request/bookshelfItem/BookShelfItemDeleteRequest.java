package com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShelfItemDeleteRequest {
    String bookShelfId;
    String novelId;
    String bookShelfItemId;
}
