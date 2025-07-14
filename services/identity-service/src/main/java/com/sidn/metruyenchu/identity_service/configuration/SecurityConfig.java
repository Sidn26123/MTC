package com.sidn.metruyenchu.identity_service.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/users","/users/registration", "/auth/token",
            "/auth/introspect", "/auth/logout", "/auth/refresh",
            "/permissions/token", "/auth/social-login", "/auth/social/callback",
            "/identity/auth/social-login",
            "/identity/auth/social/callback",
    };
    //    private final String[]
    @Value("${jwt.signerKey}")
    private String signerKey;

    //    @Autowired
    private CustomJWTDecoder customJWTDecoder;
    public SecurityConfig(CustomJWTDecoder customJwtDecoder) {
        this.customJWTDecoder = customJwtDecoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
                .permitAll()
                .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(customJWTDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .cors(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request ->
//                        request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
//                                    .permitAll()
////                                .requestMatchers(HttpMethod.GET, "/users")
////                                    .hasRole(Role.ADMIN.name())
//                                .anyRequest().authenticated());
//
//        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
//                        .decoder(customJWTDecoder)
//                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//
//
//        return httpSecurity.build();
//    }
//@Bean
//public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity
//            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Kích hoạt CORS
//            .authorizeHttpRequests(request ->
//                    request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
//                            .anyRequest().authenticated()
//            )
//            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
//                            .decoder(customJWTDecoder)
//                            .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
//            .csrf(AbstractHttpConfigurer::disable);
//
//    return httpSecurity.build();
//}
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*")); // Chỉ định frontend
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    ////        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//    @Bean
//    JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
//
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }

//    @Bean
//    public CorsFilter corsFilter(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedHeader("*");
//
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}


//package com.sidn.metruyenchu.identity_service.configuration;
//
//import com.sidn.metruyenchu.identity_service.service.AuthenticationService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
////@RequiredArgsConstructor
//@Slf4j
//public class SecurityConfig {
//
//    private final String[] PUBLIC_ENDPOINTS = {"/users","/users/registration",
//            "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh",
//            "/permissions/token", "/auth/google/callback"};
////    private final String[]
//    @Value("${jwt.signerKey}")
//    private String signerKey;
//
////    private final AuthenticationService authenticationService;
//
////    @Autowired
//    private CustomJWTDecoder customJWTDecoder;
//    public SecurityConfig(CustomJWTDecoder customJwtDecoder) {
//        this.customJWTDecoder = customJwtDecoder;
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
////                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
////                .permitAll()
////                .anyRequest()
////                .authenticated());
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
//                        .requestMatchers("/auth/google/callback").permitAll()
//                        .anyRequest().authenticated())
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/auth/login") // Trang đăng nhập tùy chỉnh nếu cần
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .oidcUserService(oidcUserService())) // Sử dụng service tùy chỉnh để xử lý thông tin người dùng
//                        .successHandler((request, response, authentication) -> {
//                            // Xử lý sau khi đăng nhập thành công
//                            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
//                            String jwt = authenticationService.generateJwtForGoogleUser(oidcUser);
//                            response.sendRedirect("/auth/google/success?token=" + jwt); // Chuyển hướng với JWT
//                        }))
//
//        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
//                        .decoder(customJWTDecoder)
//                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        return userRequest -> {
//            OidcUser oidcUser = new DefaultOAuth2UserService().loadUser(userRequest);
//            // Xử lý thông tin người dùng từ Google (ví dụ: lưu vào DB, gán vai trò)
//            authenticationService.handleGoogleUser(oidcUser);
//            return oidcUser;
//            OidcUser oidcUser = new DefaultOAuth2UserService().loadUser(userRequest);
//            // Xử lý thông tin người dùng từ Google (ví dụ: lưu vào DB, gán vai trò)
//            authenticationService.handleGoogleUser(oidcUser);
//            return oidcUser;
//        }
//    }
//
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
////
////        httpSecurity
////                .cors(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(request ->
////                        request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
////                                    .permitAll()
//////                                .requestMatchers(HttpMethod.GET, "/users")
//////                                    .hasRole(Role.ADMIN.name())
////                                .anyRequest().authenticated());
////
////        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
////                        .decoder(customJWTDecoder)
////                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
////                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
////        httpSecurity.csrf(AbstractHttpConfigurer::disable);
////
////
////        return httpSecurity.build();
////    }
////@Bean
////public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
////    httpSecurity
////            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Kích hoạt CORS
////            .authorizeHttpRequests(request ->
////                    request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
////                            .anyRequest().authenticated()
////            )
////            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
////                            .decoder(customJWTDecoder)
////                            .jwtAuthenticationConverter(jwtAuthenticationConverter()))
////                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
////            .csrf(AbstractHttpConfigurer::disable);
////
////    return httpSecurity.build();
////}
////
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(List.of("*")); // Chỉ định frontend
////        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//////        configuration.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
////    @Bean
////    JwtDecoder jwtDecoder() {
////        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
////
////        return NimbusJwtDecoder
////                .withSecretKey(secretKeySpec)
////                .macAlgorithm(MacAlgorithm.HS512)
////                .build();
////    }
//
////    @Bean
////    public CorsFilter corsFilter(){
////        CorsConfiguration corsConfiguration = new CorsConfiguration();
////
////        corsConfiguration.addAllowedOrigin("*");
////        corsConfiguration.addAllowedMethod("*");
////        corsConfiguration.addAllowedHeader("*");
////
////        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
////        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
////
////        return new CorsFilter(urlBasedCorsConfigurationSource);
////    }
//
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter(){
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//
//        return jwtAuthenticationConverter;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(10);
//    }
//
//}