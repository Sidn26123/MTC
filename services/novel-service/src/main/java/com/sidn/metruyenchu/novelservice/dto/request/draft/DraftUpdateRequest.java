package com.sidn.metruyenchu.novelservice.dto.request.draft;
import com.sidn.metruyenchu.novelservice.enums.DraftState;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DraftUpdateRequest {
    String title;
    String content;
    String note;
    String novelId;
    String chapterId;
    DraftState state;
}
