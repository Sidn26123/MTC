package com.sidn.metruyenchu.fileservice.entity;

import com.sidn.metruyenchu.fileservice.enums.FilePurposeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class NovelFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String novelId;

    String chapterId;

    String displayName;

    String fileName;

    String relativePath;

    FilePurposeEnum purpose;

    @Builder.Default
    Boolean isDeleted = false;
}
