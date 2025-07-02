package com.sidn.metruyenchu.novelservice.dto.request.genre;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenreUpdateRequest {

    @Size(min = 4, message = "GENRE_NAME_TOO_SHORT")
    @Size(max = 255, message = "GENRE_NAME_TOO_LONG")
    String name;
}
