package com.sidn.metruyenchu.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String profilePicture;
    short gender;
    LocalDate birthDate;
    Set<RoleResponse> roles;

}
