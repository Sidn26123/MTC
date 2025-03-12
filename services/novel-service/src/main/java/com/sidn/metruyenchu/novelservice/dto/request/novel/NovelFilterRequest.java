package com.sidn.metruyenchu.novelservice.dto.request.novel;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovelFilterRequest {
    String searchText;
    List<String> sects;
    List<String> worldScenes;
    List<String> mainCharacterTraits;
    List<String> genres;
    List<String> status;
    String sortBy = "created_at";
    String sortOrder = "DESC";
    int page = 1;
    int size = 10;
}
