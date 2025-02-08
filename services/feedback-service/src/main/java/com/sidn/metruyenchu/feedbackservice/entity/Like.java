package com.sidn.metruyenchu.feedbackservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String liker;

    @Column(nullable = false)
    String targetId;

    

    @Builder.Default
    Boolean isLiked = true;
}
