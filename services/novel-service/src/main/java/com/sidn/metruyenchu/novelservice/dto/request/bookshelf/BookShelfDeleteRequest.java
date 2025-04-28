package com.sidn.metruyenchu.novelservice.dto.request.bookshelf;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShelfDeleteRequest {
    String bookShelfId;
}
