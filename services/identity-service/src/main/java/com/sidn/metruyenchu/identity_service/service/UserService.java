package com.sidn.metruyenchu.identity_service.service;

import com.sidn.metruyenchu.identity_service.dto.request.GoogleLoginRequest;
import com.sidn.metruyenchu.identity_service.dto.request.UserCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.request.UserUpdateRequest;
import com.sidn.metruyenchu.identity_service.dto.request.feignclient.UserProfileCreationRequest;
import com.sidn.metruyenchu.identity_service.dto.response.UserResponse;
import com.sidn.metruyenchu.identity_service.entity.Role;
import com.sidn.metruyenchu.identity_service.entity.User;
//import com.sidn.metruyenchu.identity_service.enums.Role;
import com.sidn.metruyenchu.identity_service.exception.AppException;
import com.sidn.metruyenchu.identity_service.exception.ErrorCode;
import com.sidn.metruyenchu.identity_service.mapper.UserMapper;
import com.sidn.metruyenchu.identity_service.mapper.feignclient.NovelStatusMapper;
import com.sidn.metruyenchu.identity_service.repository.RoleRepository;
import com.sidn.metruyenchu.identity_service.repository.UserRepository;
import com.sidn.metruyenchu.identity_service.repository.httpclient.NovelClient;
import com.sidn.metruyenchu.identity_service.repository.httpclient.UserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    @NonFinal
    @Value("${file.default-avatar-file-name}")
    String defaultAvatarFileName;
    RoleRepository roleRepository;
    UserRepository userRepository;
    NovelClient novelClient;
    UserClient userClient;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    NovelStatusMapper novelStatusMapper;
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request){
        User user = userMapper.toUser(request);
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        request.getRoles().forEach(
                roleId -> {
                    Optional<Role> role = roleRepository.findById(roleId);
                    if (role.isPresent()){
                        roles.add(role.get());
                    } else {
                        throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
                    }
                }
        );
//        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        user = userRepository.save(user);
//        try {
//            user = userRepository.save(user);
//        } catch (DataIntegrityViolationException exception){
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }

        //test feignclient
//        novelClient.createNovelStatus(novelStatusMapper.toNovelStatusCreationRequest(request));
        UserProfileCreationRequest userProfileCreationRequest = request.getUserProfileCreationRequest();
        if (userProfileCreationRequest.getAvatarPath() == null){
            userProfileCreationRequest.setAvatarPath(defaultAvatarFileName);
        }
        userClient.createUserProfile(request.getUserProfileCreationRequest());


        kafkaTemplate.send("user-creation-successful", "Welcome" + user.getUsername());

        return userMapper.toUserResponse(user);
    }

    //hasAuthority
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        return userMapper.toUserResponses(userRepository.findAll());
    }

    public UserResponse getUser(String userId){
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }


    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));


        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        return userMapper.toUserResponse(user);
    }

//    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserByUsername(String username){
        return userMapper.toUserResponse(
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
        );
    }

    public User loginSocial(GoogleLoginRequest googleLoginRequest) throws Exception {
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
            }
        }


        User user = optionalUser.get();

//        // Kiểm tra nếu tài khoản bị khóa
//        if (!user.isActive()) {
//            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
//        }

        return user;
        // Tạo JWT token cho người dùng
//        return jwtTokenUtil.generateToken(user);
    }


}