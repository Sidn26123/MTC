package com.sidn.metruyenchu.novelservice.dto.response.bookshelf;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShelfResponse {
    String id;
    String userId;
    Boolean isActive;
    Boolean isDeleted;
}
