package com.sidn.metruyenchu.identity_service.service;

import com.sidn.metruyenchu.identity_service.dto.request.UserCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.response.UserResponse;
import com.sidn.metruyenchu.identity_service.entity.User;
import com.sidn.metruyenchu.identity_service.exception.AppException;
import com.sidn.metruyenchu.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;
    private User user;
    @BeforeEach
    public void initData(){
        dob = LocalDate.of(2000, 01,01);

        request = UserCreationRequest.builder()
                .username("sidn21s2")
                .password("34564354")
                .email("sa@gmail.com")
                .firstName("Uy")
                .lastName("Nguyen")
                .build();

        userResponse = UserResponse.builder()
                .id("abc")
                .username("sidn21s2")
                .birthDate(dob)
                .firstName("Uy")
                .lastName("Nguyen")
                .profilePicture(null)
                .email("sa@gmail.com")
                .build();

        user = User.builder()
                .id("abc")
                .username("sidn21s2")
                .email("sa@gmail.com")
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);
        // THEN

        Assertions.assertThat(response.getId()).isEqualTo("abc");
        Assertions.assertThat(response.getUsername()).isEqualTo("sidn21s2");
    }

    @Test
    void createUser_userExisted_fail(){
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode())
                .isEqualTo(1001);
    }
}
