package com.sidn.metruyenchu.paymentservice.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class PublicEndpointLoggerFilter extends OncePerRequestFilter {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/payments/.*", "/payments/**", "/payment/vn-pay-callback",
            "/vn-pay-callback", "vn-pay-callback"
    };

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        boolean isPublic = Arrays.stream(PUBLIC_ENDPOINTS)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (isPublic) {
            log.info("✅ PUBLIC request: {}", path);
        } else {
            log.info("🔐 PROTECTED request: {}", path);
        }

        filterChain.doFilter(request, response);
    }
}
