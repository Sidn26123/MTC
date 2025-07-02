package com.sidn.metruyenchu.novelservice.dto.request.mainTrait;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainCharacterTraitCreationRequest {
    @Size(min = 4, message = "MAIN_CHARACTER_TRAIT_NAME_TOO_SHORT")
    @Size(max = 255, message = "MAIN_CHARACTER_TRAIT_NAME_TOO_LONG")
    String name;
}
