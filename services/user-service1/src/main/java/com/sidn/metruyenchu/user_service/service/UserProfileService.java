package com.sidn.metruyenchu.user_service.service;

import com.sidn.metruyenchu.user_service.exception.AppException;
import com.sidn.metruyenchu.user_service.exception.ErrorCode;
import com.sidn.metruyenchu.user_service.dto.request.UserProfile.UserProfileCreationRequest;
import com.sidn.metruyenchu.user_service.dto.request.UserProfile.UserProfileUpdateRequest;
import com.sidn.metruyenchu.user_service.dto.response.UserProfile.UserProfileResponse;
import com.sidn.metruyenchu.user_service.entity.UserProfile;
import com.sidn.metruyenchu.user_service.mapper.UserProfileMapper;
import com.sidn.metruyenchu.user_service.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;


    public UserProfileResponse createUserProfile(UserProfileCreationRequest request){
        log.info(request.getEmail());
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        log.info(userProfile.getEmail());
        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse updateUserProfile(String profileId, UserProfileUpdateRequest request){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(profileId);
        if(userProfileOptional.isPresent()){
            UserProfile userProfile = userProfileOptional.get();
            userProfileMapper.updateUserProfile(userProfile, request);
            userProfile = userProfileRepository.save(userProfile);
            return userProfileMapper.toUserProfileResponse(userProfile);
        }
        else {
            throw new AppException(ErrorCode.PROFILE_NOT_FOUND);
        }
    }

    public UserProfileResponse getUserProfileByUsername(String username){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUsername(username);

        if (userProfileOptional.isEmpty()){
            throw new AppException(ErrorCode.PROFILE_NOT_FOUND);
        }
        log.info(userProfileOptional.get().getUserId());
        return userProfileMapper.toUserProfileResponse(userProfileOptional.get());


    }

    public UserProfileResponse getUserProfileByUserId(String userId){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserId(userId);

        if (userProfileOptional.isEmpty()){
            throw new AppException(ErrorCode.PROFILE_NOT_FOUND);
        }
        return userProfileMapper.toUserProfileResponse(userProfileOptional.get());


    }

    public UserProfileResponse getUserProfile(String profileId){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(profileId);
        if (userProfileOptional.isEmpty()){
            throw new AppException(ErrorCode.PROFILE_NOT_FOUND);
        }
        return userProfileMapper.toUserProfileResponse(userProfileOptional.get());
    }

    public void deleteUserProfile(String profileId){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(profileId);
        if (userProfileOptional.isPresent()){
            UserProfile userProfile = userProfileOptional.get();
            userProfile.setDeleted(true);
            userProfileRepository.save(userProfileOptional.get());
        }
        else {
            throw new AppException(ErrorCode.PROFILE_NOT_FOUND);
        }
    }


}
