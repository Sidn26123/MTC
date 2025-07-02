package com.sidn.metruyenchu.fileservice.dto.response.feign;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelResponse {
    String id;
    String name;
    String displayName;
    String slug;
    String description;
    String novelCoverImage;
    String originalName;
    String originalLink;
    String currentPublisher;
    Integer chapterReadToComment;
    Integer chapterReadToRate;
    Integer fullSetPurchaseDiscount;
    Integer wordCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
