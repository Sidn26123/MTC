package com.sidn.metruyenchu.user_service.controller;

import com.nimbusds.jwt.SignedJWT;
import com.sidn.metruyenchu.user_service.dto.ApiResponse;
import com.sidn.metruyenchu.user_service.dto.request.UserProfile.UserProfileCreationRequest;
import com.sidn.metruyenchu.user_service.dto.request.UserProfile.UserProfileUpdateRequest;
import com.sidn.metruyenchu.user_service.dto.response.UserProfile.UserProfileResponse;
import com.sidn.metruyenchu.user_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.ParseException;

@RestController
@RequestMapping("/user-profiles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {


    UserProfileService userProfileService;
    

    @PostMapping
    public ApiResponse<UserProfileResponse> createUserProfile(@RequestBody UserProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createUserProfile(request))
                .build();
    }

    @PutMapping("/{profileId}")
    public ApiResponse<UserProfileResponse> updateUserProfile(@PathVariable String profileId, @RequestBody UserProfileUpdateRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.updateUserProfile(profileId, request))
                .build();
    }
    @GetMapping("")
    public ApiResponse<UserProfileResponse> getUserProfileByUsername(@RequestParam String username) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getUserProfileByUsername(username))
                .build();
    }
    @GetMapping("/byId")
    public ApiResponse<UserProfileResponse> getUserProfileByUserId(@RequestParam String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getUserProfileByUserId(userId))
                .build();
    }



    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> getProfileByToken() throws ParseException {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = servletRequestAttributes.getRequest().getHeader("Authorization").substring(7);
        log.info("token: {}", token);
        SignedJWT signedJWT = SignedJWT.parse(token);
        String username = signedJWT.getJWTClaimsSet().getSubject();
        log.info("Username: {}", username);
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getUserProfileByUsername(username))
                .build();
    }

    @GetMapping("/{profileId}")
    public ApiResponse<UserProfileResponse> getUserProfile(@PathVariable String profileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getUserProfile(profileId))
                .build();
    }

    @DeleteMapping("/{profileId}")
    public ApiResponse<Void> deleteUserProfile(@PathVariable String profileId) {
        userProfileService.deleteUserProfile(profileId);
        return ApiResponse.<Void>builder()
                .build();
    }

}
