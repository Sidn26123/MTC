package com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShelfItemGetRequest extends BaseFilterRequest {
    String bookShelfId;
    String novelId;
    String userId;
}
