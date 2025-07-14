package com.sidn.metruyenchu.identity_service.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.nimbusds.jose.JOSEException;
import com.sidn.metruyenchu.identity_service.dto.request.*;
import com.sidn.metruyenchu.identity_service.dto.response.AuthenticationResponse;
import com.sidn.metruyenchu.identity_service.dto.response.IntrospectResponse;
import com.sidn.metruyenchu.identity_service.dto.response.ResponseObject;
import com.sidn.metruyenchu.identity_service.entity.User;
import com.sidn.metruyenchu.identity_service.service.AuthenticationService;
import com.sidn.metruyenchu.identity_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;
    UserService userService;


    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        log.info("Authentication request: {}", request);
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var results = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(results)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }


    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/social-login")
    public ResponseEntity<String> socialAuth(
            HttpServletRequest request
    ){
        //request.getRequestURI()
        String url = authenticationService.generateAuthUrl();
        return ResponseEntity.ok(url);
    }



//    private AuthenticationResponse loginSocial(
//            @Valid @RequestBody GoogleLoginRequest googleLoginRequest,
//            HttpServletRequest request
//    ) throws Exception {
//        // Gọi hàm loginSocial từ UserService cho đăng nhập mạng xã hội
//        User user = userService.loginSocial(googleLoginRequest);
//        var token = generateToken(user);
//        var refreshToken = generateRefreshToken(user);
//
//        // Xử lý token và thông tin người dùng
//        String userAgent = request.getHeader("User-Agent");
//        User userDetail = userService.getUserDetailsFromToken(token);
//        Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));
//
//        // Tạo đối tượng LoginResponse
//        LoginResponse loginResponse = LoginResponse.builder()
//                .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
//                .token(jwtToken.getToken())
//                .tokenType(jwtToken.getTokenType())
//                .refreshToken(jwtToken.getRefreshToken())
//                .username(userDetail.getUsername())
//                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
//                .id(userDetail.getId())
//                .build();
//
//        // Trả về phản hồi
//        return ResponseEntity.ok().body(
//                ResponseObject.builder()
//                        .message("Login successfully")
//                        .data(loginResponse)
//                        .status(HttpStatus.OK)
//                        .build()
//        );
//    }

    @GetMapping("/social/callback")
    public ResponseEntity<ResponseObject> callback(
//    public ResponseEntity<AuthenticationResponse> callback(
            @RequestParam("code") String code,
            HttpServletRequest request
    ) throws Exception {
        // Call the AuthService to get user info
        Map<String, Object> userInfo = authenticationService.authenticateAndFetchProfile(code);

        if (userInfo == null) {
            return ResponseEntity.badRequest().body(new ResponseObject(
                    "Failed to authenticate", HttpStatus.BAD_REQUEST, null
            ));
        }

        // Extract user information from userInfo map
        String accountId = "";
        String name = "";
        String avatarPath = "";
        String email = "";


        accountId = (String) Objects.requireNonNullElse(userInfo.get("sub"), "");
        name = (String) Objects.requireNonNullElse(userInfo.get("name"), "");
        avatarPath = (String) Objects.requireNonNullElse(userInfo.get("picture"), "");
        email = (String) Objects.requireNonNullElse(userInfo.get("email"), "");

//        User user = userService.getUserByEmail(email);
//        if (user == null) {
//            throw new Exception("Invalid email");
//        }
        // Tạo đối tượng UserLoginDTO
        GoogleLoginRequest googleLoginRequest = GoogleLoginRequest.builder()
                .email(email)
                .username(name)
                .avatarPath(avatarPath)
                .build();


        googleLoginRequest.setGoogleId(accountId);

        AuthenticationResponse authResponse = authenticationService.loginSocial(googleLoginRequest);
        return ResponseEntity.ok(
                new ResponseObject("Login successfully", HttpStatus.OK, authResponse)
        );



        //return this.loginSocial(googleLoginRequest, request);
    }
}
