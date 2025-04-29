package com.sidn.metruyenchu.novelservice.dto.request.novel;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelOfAuthorFilterRequest extends BaseFilterRequest {
    String authorId;

}
