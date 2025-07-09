package com.sidn.metruyenchu.identity_service.dto.request;

import com.sidn.metruyenchu.identity_service.dto.request.feignclient.UserProfileCreationRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;

//    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    @Email
    String email;

    String firstName;
    String lastName;
    List<String> roles;
//    String profilePicture;
//    short gender;
//    @BirthDateConstraint(min = 18, message="INVALID_BIRTHDAY")
//    LocalDate birthDate;

    UserProfileCreationRequest userProfileCreationRequest;

}