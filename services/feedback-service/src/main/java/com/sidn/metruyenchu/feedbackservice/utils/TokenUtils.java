package com.sidn.metruyenchu.feedbackservice.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.text.ParseException;

public class TokenUtils {
    private static final String SECRET_KEY = "cS6BxiaKDa2unnbvzjzObquWcvFQjtXgnNByk3qRoQ+dtnTykVToROhj4y86bpK6";
    public static String getUserIdFromToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            return (String) signedJWT.getJWTClaimsSet().getClaims().get("user_id");
        }
        catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTokenFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getCredentials() instanceof Jwt) {
            return ((Jwt) authentication.getCredentials()).getTokenValue();
        }
        return null;
    }

    public static String getUserIdFromContext() {
        String token = getTokenFromContext();
        if (token != null) {
            return getUserIdFromToken(token);
        }
        return null;
    }


}
