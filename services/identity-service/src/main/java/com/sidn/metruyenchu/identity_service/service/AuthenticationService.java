package com.sidn.metruyenchu.identity_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sidn.metruyenchu.identity_service.dto.request.*;
import com.sidn.metruyenchu.identity_service.dto.request.feignclient.UserProfileCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.response.AuthenticationResponse;
import com.sidn.metruyenchu.identity_service.dto.response.IntrospectResponse;
import com.sidn.metruyenchu.identity_service.entity.InvalidatedToken;
import com.sidn.metruyenchu.identity_service.entity.Role;
import com.sidn.metruyenchu.identity_service.entity.User;
import com.sidn.metruyenchu.identity_service.exception.AppException;
import com.sidn.metruyenchu.identity_service.exception.ErrorCode;
import com.sidn.metruyenchu.identity_service.repository.InvalidatedRepository;
import com.sidn.metruyenchu.identity_service.repository.UserRepository;
import com.sidn.metruyenchu.identity_service.repository.httpclient.UserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedRepository invalidatedRepository;

    @NonFinal
    @Value("${file.default-avatar-file-name}")
    String defaultAvatarFileName;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    @Value("${google.client-id}")
    private String googleClientId;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @NonFinal
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUri;

    UserClient userClient;


    public String generateAuthUrl() {
        String url = "";

        GoogleAuthorizationCodeRequestUrl urlBuilder = new GoogleAuthorizationCodeRequestUrl(
                googleClientId,
                googleRedirectUri,
                Arrays.asList("email", "profile", "openid"));
        url = urlBuilder.build();
        return url;
    }



    public Map<String, Object> authenticateAndFetchProfile(String code) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String accessToken;
        //Gson gson = new Gson();
        try {
            accessToken = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    googleClientId,
                    googleClientSecret,
                    code,
                    googleRedirectUri
            ).execute().getAccessToken();
        } catch (TokenResponseException e) {
            System.err.println("Google OAuth token error: " + e.getMessage());
            if (e.getDetails() != null) {
                System.err.println("Error: " + e.getDetails().getError());
                System.err.println("Description: " + e.getDetails().getErrorDescription());
                System.err.println("URI: " + e.getDetails().getErrorUri());
            }
            throw e;
        }


        // Configure RestTemplate to include the access token in the Authorization header
        restTemplate.getInterceptors().add((req, body, executionContext) -> {
            req.getHeaders().set("Authorization", "Bearer " + accessToken);
            return executionContext.execute(req, body);
        });

        // Make a GET request to fetch user information
        return new ObjectMapper().readValue(
                restTemplate.getForEntity(googleUserInfoUri, String.class).getBody(),
                new TypeReference<>() {
                });
        //break;
    }

    public AuthenticationResponse loginSocial(GoogleLoginRequest googleLoginRequest) throws Exception {
        Optional<User> optionalUser = Optional.empty();
        //  Role roleUser = com.sidn.metruyenchu.identity_service.enums.Role.USER.toString();
        // Kiểm tra Google Account ID
        if (googleLoginRequest.isGoogleAccountIdValid()) {
            optionalUser = userRepository.findByGoogleId(googleLoginRequest.getGoogleId());

            // Tạo người dùng mới nếu không tìm thấy
            if (optionalUser.isEmpty()) {

                User newUser = User.builder()
                        .username(Optional.ofNullable(googleLoginRequest.getUsername()).orElse("") )
                        .email(Optional.ofNullable(googleLoginRequest.getEmail()).orElse(""))
//                        .role(roleUser)
                        .googleId(googleLoginRequest.getGoogleId())
                        .password("") // Mật khẩu trống cho đăng nhập mạng xã hội
//                        .active(true)
                        .build();

                // Lưu người dùng mới


                newUser = userRepository.save(newUser);
                optionalUser = Optional.of(newUser);

                LocalDate localDate = LocalDate.of(1999, 10, 15);
                Date dateOfBirth = java.sql.Date.valueOf(localDate);

                UserProfileCreationRequest userProfileCreationRequest = UserProfileCreationRequest
                        .builder()
                        .username(newUser.getUsername())
                        .email(newUser.getEmail())
                        .avatarPath(googleLoginRequest.getAvatarPath())
                        .status("ACTIVE")
                        .gender("MALE") // M
                        .firstName("")
                        .lastName("")
                        .userId(newUser.getId())
                        .dateOfBirth(dateOfBirth)
                        .build();
                if (userProfileCreationRequest.getAvatarPath() == null){
                    userProfileCreationRequest.setAvatarPath(defaultAvatarFileName);
                }
                userClient.createUserProfile(userProfileCreationRequest);



            }
        }


        User user = optionalUser.get();

//        // Kiểm tra nếu tài khoản bị khóa
//        if (!user.isActive()) {
//            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
//        }

        var token = generateToken(user);
        var refreshToken = generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .refreshToken(refreshToken)
                .build();

    }


    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e){
            isValid = false;
        }

        return  IntrospectResponse.builder()
                .valid(isValid)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);
        var refreshToken = generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .refreshToken(refreshToken)
                .build();
//        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }


    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();


            invalidatedRepository.save(invalidatedToken);
        } catch (AppException e){
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }


    }

//    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
//        var signedJWT = verifyToken(request.getToken(), true);
//
//        String jit = signedJWT.getJWTClaimsSet().getJWTID();
//        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
//                .id(jit)
//                .expiryTime(expiryTime)
//                .build();
//
//        invalidatedRepository.save(invalidatedToken);
//
//        var username = signedJWT.getJWTClaimsSet().getSubject();
//        var user = userRepository.findByUsername(username).orElseThrow(
//                () -> new AppException(ErrorCode.UNAUTHENTICATED)
//        );
//
//        var token = generateToken(user);
//
//        return AuthenticationResponse.builder()
//                .token(token)
//                .authenticated(true)
//                .build();
//    }
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true); // Kiểm tra và xác thực token

        // Kiểm tra loại token (đảm bảo là refresh token)
        String tokenType = signedJWT.getJWTClaimsSet().getStringClaim("token_type");
        if (!"refresh".equals(tokenType)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        // Vô hiệu hóa refresh token hiện tại
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedRepository.save(invalidatedToken);

        // Tìm lại user
        String username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        // Sinh token mới
        var accessToken = generateToken(user);
        var refreshToken = generateRefreshToken(user); // Sinh refresh token mới

        return AuthenticationResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }


//    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
//
//        SignedJWT signedJWT = SignedJWT.parse(token);
//        //Lay thong tin user_id
////        System.out.println(signedJWT.getJWTClaimsSet().getClaims().get("user_id"));
//        Date expiration = (isRefresh)
//                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
//                    .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
//                : signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        var verified = signedJWT.verify(verifier);
//
//        if (!(verified && expiration.after(new Date())))
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//
//        if (invalidatedRepository
//                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//
//        return signedJWT;
//    }
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        boolean verified = signedJWT.verify(verifier);
        if (!verified) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Kiểm tra expiration thực tế từ JWT
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expiration.before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Nếu là yêu cầu refresh, kiểm tra token_type
        if (isRefresh) {
            String tokenType = signedJWT.getJWTClaimsSet().getStringClaim("token_type");
            if (!"refresh".equals(tokenType)) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
        }

        // Kiểm tra xem token đã bị vô hiệu hóa chưa
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidatedRepository.existsById(jit)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }


    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("sidn")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("user_id", user.getId())
                .claim("token_type", "access") // Thêm claim token_type
                .claim("email", user.getEmail())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
//            log.error("loi khi tao token");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private String generateRefreshToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("sidn")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("token_type", "refresh") // Thêm claim token_type
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
//            log.error("loi khi tao token");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role->{
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission->{
                        stringJoiner.add(permission.getName());
                    });
                }

            });

        return stringJoiner.toString();
    }



}
