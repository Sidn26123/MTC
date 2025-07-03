package com.sidn.metruyenchu.identity_service.dto.request.feignclient;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequest {
    String username;
    String email;
    String avatarPath;
    String status;
    String gender;
    String firstName;
    String lastName;
    Date dateOfBirth;
}
