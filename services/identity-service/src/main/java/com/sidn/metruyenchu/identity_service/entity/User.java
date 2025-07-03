package com.sidn.metruyenchu.identity_service.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "username", unique = true)
    String username;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    String email;

    @Column(updatable = false, nullable = false) //không cho phép cập nhật thủ công
    @CreationTimestamp
    Timestamp createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    Timestamp updatedAt;
    Timestamp deletedAt;
    @Nullable
    Timestamp lastLogin;
    boolean isDeleted = false;
    boolean isEnabled = false;
    boolean isLocked = false;

    @ManyToMany
    Set<Role> roles;
}