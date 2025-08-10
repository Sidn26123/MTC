package com.sidn.metruyenchu.identity_service.controller;

import com.sidn.metruyenchu.identity_service.dto.request.ApiResponse;
import com.sidn.metruyenchu.identity_service.dto.request.UserCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.UserUpdateRequest;
import com.sidn.metruyenchu.identity_service.dto.response.UserResponse;
import com.sidn.metruyenchu.identity_service.service.UserService;
import com.sidn.metruyenchu.shared_library.dto.BaseFilterRequest;
import com.sidn.metruyenchu.shared_library.dto.PageResponse;
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
    ApiResponse<PageResponse<UserResponse>> getAllUsers(BaseFilterRequest request) {
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getUsers(request))
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

    @GetMapping("/getAdmin")
    ApiResponse<List<UserResponse>> getAdminUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAdminUsers())
                .build();
    }

    @GetMapping("/checkPassword")
    ApiResponse<Boolean> checkPassword(@RequestParam String userId, @RequestParam String password) {
        boolean isValid = userService.checkPassword(userId, password);
        return ApiResponse.<Boolean>builder()
                .result(isValid)
                .build();
    }
}