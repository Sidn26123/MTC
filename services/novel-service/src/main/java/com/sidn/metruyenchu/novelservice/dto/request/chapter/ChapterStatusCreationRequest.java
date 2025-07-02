package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterStatusCreationRequest {
    @Size(min = 4, message = "CHAPTER_STATUS_NAME_TOO_SHORT")
    @Size(max = 255, message = "CHAPTER_STATUS_NAME_TOO_LONG")
    String name;
}
