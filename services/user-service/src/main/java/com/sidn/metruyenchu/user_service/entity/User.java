package com.sidn.metruyenchu.user_service.entity;

import com.sidn.metruyenchu.author_admin_service.enums.AccountStatusEnums;
import com.sidn.metruyenchu.author_admin_service.enums.GenderEnums;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.sidn.metruyenchu.user_service.enums.*;

//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Entity
//@Table(name = "\"user\"")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
//
//    @Column(length = 64, unique = true, nullable = false)
//    private String username;
//
//    @Column(length = 256, unique = true, nullable = false)
//    private String email;
//
//    @Column(length = 256, nullable = false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 10, nullable = false)
//    private String status = String.valueOf(AccountStatusEnums.INACTIVE);
//
//    @Column(length = 256)
//    private String profileImage;
//
//    @Column(length = 100)
//    private String fullName;
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 10, nullable = false)
//    private String gender = String.valueOf(GenderEnums.MALE);
//
//    @Column(nullable = false)
//    private LocalDate birthDate;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//    private LocalDateTime lastLogin;
//
//    @Column(nullable = false)
//    private Boolean isDeleted = false;
//}
