package com.sidn.metruyenchu.novelservice.dto.response.novel;

import com.sidn.metruyenchu.novelservice.dto.response.GenreResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelBasicInfoResponse {
    String id;
    String name;
    String displayName;
    String slug;
    String description;
    String novelCoverImage;
    String currentPublisher;
    NovelAuthorResponse author;

    List<GenreResponse> genres;

    Integer totalChapters;

    Boolean isDeleted = false;
    Boolean isActive = true;
}
