package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterUpdateAmountFieldRequest {
    Integer amountToUnlock;

    Long viewCount;

    Integer totalComments;
}
