package com.sidn.metruyenchu.identity_service.controller;

import com.sidn.metruyenchu.identity_service.dto.request.ApiResponse;
import com.sidn.metruyenchu.identity_service.dto.request.UserCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.UserUpdateRequest;
import com.sidn.metruyenchu.identity_service.dto.response.UserResponse;
import com.sidn.metruyenchu.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/registration")
    ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping()
    ApiResponse<List<UserResponse>> getAllUsers() {
        var authentication= SecurityContextHolder.getContext().getAuthentication();

//        log.info("Username: {}", authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority ->
//                log.info("GrantedAuthority: {}", grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();

    }

    @GetMapping("/username/{username}")
    ApiResponse<UserResponse> getUserByUsername(@PathVariable("username") String username){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByUsername(username))
                .build();

    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User " + userId + " deleted";
    }

    @GetMapping("/myInfo")
    UserResponse getMyInfo(){


        return userService.getMyInfo();
    }
}