package com.sidn.metruyenchu.fileservice.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static com.sidn.metruyenchu.shared_library.utils.TokenUtils.getUserIdFromToken;

public class TokenUtils {

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