package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.ApiResponse;
import com.sidn.metruyenchu.novelservice.dto.request.mainTrait.MainCharacterTraitCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.MainCharacterTraitResponse;
import com.sidn.metruyenchu.novelservice.entity.MainCharacterTrait;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.MainCharacterTraitMapper;
import com.sidn.metruyenchu.novelservice.repository.MainCharacterTraitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MainCharacterTraitService {
    MainCharacterTraitRepository mainCharacterTraitRepository;
    MainCharacterTraitMapper mainCharacterTraitMapper;

    public MainCharacterTraitResponse createMainCharacterTrait(MainCharacterTraitCreationRequest request) {
        MainCharacterTrait mainCharacterTrait = mainCharacterTraitMapper.toMainCharacterTrait(request);
        mainCharacterTraitRepository.findByName(mainCharacterTrait.getName())
                .ifPresent(checkMainCharacterTrait -> {
                    throw new AppException(ErrorCode.MAIN_CHARACTER_TRAIT_ALREADY_EXISTED);
                });

        try {
            mainCharacterTrait = mainCharacterTraitRepository.save(mainCharacterTrait);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return mainCharacterTraitMapper.toMainCharacterTraitResponse(mainCharacterTrait);
    }

    public MainCharacterTraitResponse getMainCharacterTraitById(String mainCharacterTraitId) {
        return mainCharacterTraitRepository.findById(mainCharacterTraitId)
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .orElseThrow(() -> new AppException(ErrorCode.MAIN_CHARACTER_TRAIT_NOT_FOUND));
    }

    public List<MainCharacterTraitResponse> getMainCharacterTraitByName(String name) {
        return mainCharacterTraitRepository.findByName(name).stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .collect(Collectors.toList());
    }

    public MainCharacterTraitResponse updateMainCharacterTrait(String mainCharacterTraitId, MainCharacterTraitCreationRequest request) {
        MainCharacterTrait mainCharacterTrait = mainCharacterTraitRepository.findById(mainCharacterTraitId)
                .orElseThrow(() -> new AppException(ErrorCode.MAIN_CHARACTER_TRAIT_NOT_FOUND));
        mainCharacterTrait.setName(request.getName());
        mainCharacterTraitRepository.findByName(mainCharacterTrait.getName())
                .ifPresent(checkMainCharacterTrait -> {
                    throw new AppException(ErrorCode.MAIN_CHARACTER_TRAIT_ALREADY_EXISTED);
                });
        try {
            mainCharacterTrait = mainCharacterTraitRepository.save(mainCharacterTrait);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
        return mainCharacterTraitMapper.toMainCharacterTraitResponse(mainCharacterTrait);
    }

    public void deleteMainCharacterTrait(String mainCharacterTraitId) {
        MainCharacterTrait mainCharacterTrait = mainCharacterTraitRepository.findById(mainCharacterTraitId)
                .orElseThrow(() -> new AppException(ErrorCode.MAIN_CHARACTER_TRAIT_NOT_FOUND));
        mainCharacterTrait.setIsDeleted(true);
        try {
            mainCharacterTraitRepository.save(mainCharacterTrait);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

    }

    public List<MainCharacterTraitResponse> getAllMainCharacterTrait() {
        return mainCharacterTraitRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .collect(Collectors.toList());
    }

    public List<MainCharacterTraitResponse> getActivatingMainCharacterTrait() {
        return mainCharacterTraitRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .collect(Collectors.toList());
    }

    public List<MainCharacterTraitResponse> getAllAvailableMainCharacterTrait() {
        return mainCharacterTraitRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .collect(Collectors.toList());
    }
}
