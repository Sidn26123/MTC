package com.sidn.metruyenchu.fileservice.dto.response.feign;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse {
    String id;
    String name;
    String publisher;
    String novel;

    Boolean isDeleted;
    Boolean isActive;
}
