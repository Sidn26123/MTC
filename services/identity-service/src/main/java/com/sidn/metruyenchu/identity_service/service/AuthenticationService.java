package com.sidn.metruyenchu.identity_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sidn.metruyenchu.identity_service.dto.request.AuthenticationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.IntrospectRequest;
import com.sidn.metruyenchu.identity_service.dto.request.LogoutRequest;
import com.sidn.metruyenchu.identity_service.dto.request.RefreshRequest;
import com.sidn.metruyenchu.identity_service.dto.response.AuthenticationResponse;
import com.sidn.metruyenchu.identity_service.dto.response.IntrospectResponse;
import com.sidn.metruyenchu.identity_service.entity.InvalidatedToken;
import com.sidn.metruyenchu.identity_service.entity.User;
import com.sidn.metruyenchu.identity_service.exception.AppException;
import com.sidn.metruyenchu.identity_service.exception.ErrorCode;
import com.sidn.metruyenchu.identity_service.repository.InvalidatedRepository;
import com.sidn.metruyenchu.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedRepository invalidatedRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

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
