package com.sidn.metruyenchu.novelservice.dto.request.chapter;

import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.enums.ChapterState;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterUpdateRequest {
    @Size(min = 4, message = "CHAPTER_NAME_TOO_SHORT")
    @Size(max = 255, message = "CHAPTER_NAME_TOO_LONG")
    String name;
    String publisher;
    Integer chapterIdx;
    ChapterState status;
    List<ChapterStatusResponse> chapterStatus;
    String novelId;

}
