package com.sidn.metruyenchu.identity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String password;
     String email;
     String firstName;
     String lastName;
//     String profilePicture;
//     short gender;
//
//     @BirthDateConstraint(min = 18, message="INVALID_BIRTHDAY")
//     LocalDate birthDate;
     List<String> roles;

}