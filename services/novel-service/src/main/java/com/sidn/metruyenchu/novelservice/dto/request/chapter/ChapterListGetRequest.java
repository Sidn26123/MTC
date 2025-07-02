package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterListGetRequest extends BaseFilterRequest {
    String novelId;
    String novelSlug;
}
