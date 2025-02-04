package com.sidn.metruyenchu.novelservice.dto.request;

import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterCreationRequest {
    String name;
    String publisher;
    Integer chapterIdx;
    Set<ChapterStatus> status;
    NovelVisibility visibility;
}
