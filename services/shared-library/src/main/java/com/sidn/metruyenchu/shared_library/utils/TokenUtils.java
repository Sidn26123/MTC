package com.sidn.metruyenchu.shared_library.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;


import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static List<String> getRolesFromToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            if (!signedJWT.verify(verifier)) {
                return Collections.emptyList(); // Token không hợp lệ
            }

            String scope = (String) signedJWT.getJWTClaimsSet().getClaim("scope");

            if (scope != null && !scope.isEmpty()) {
                // Tách scope theo khoảng trắng để lấy từng role
                return Arrays.asList(scope.split(" "));
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // Trả về list rỗng nếu lỗi
    }
}
