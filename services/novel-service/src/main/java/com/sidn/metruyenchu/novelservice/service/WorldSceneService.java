package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.worldScene.WorldSceneCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.WorldSceneResponse;
import com.sidn.metruyenchu.novelservice.entity.WorldScene;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.WorldSceneMapper;
import com.sidn.metruyenchu.novelservice.repository.WorldSceneRepository;
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
public class WorldSceneService {
    WorldSceneRepository worldSceneRepository;
    WorldSceneMapper worldSceneMapper;

    //CRUD world scene
    //get all world scene
    public List<WorldSceneResponse> getAllWorldScene() {
        return worldSceneRepository.findAll().stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .collect(Collectors.toList());
    }

    //get world scene by id
    public WorldSceneResponse getWorldSceneById(String worldSceneId) {
        return worldSceneRepository.findById(worldSceneId)
                .map(worldSceneMapper::toWorldSceneResponse)
                .orElseThrow(() -> new AppException(ErrorCode.WORLD_SCENE_NOT_FOUND)
                );
    }

    //create world scene
    public WorldSceneResponse createWorldScene(WorldSceneCreationRequest request) {
        WorldScene worldScene = worldSceneMapper.toWorldScene(request);

        worldSceneRepository.findByName(worldScene.getName())
                .ifPresent(checkWorldScene -> {
                    throw new AppException(ErrorCode.WORLD_SCENE_ALREADY_EXISTED);
                });
        try {
            worldScene = worldSceneRepository.save(worldScene);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return worldSceneMapper.toWorldSceneResponse(worldScene);
    }

    public WorldSceneResponse updateWorldScene(String worldSceneId, WorldSceneCreationRequest request) {
        WorldScene worldScene = worldSceneRepository.findById(worldSceneId)
                .orElseThrow(() -> new AppException(ErrorCode.WORLD_SCENE_NOT_FOUND));
        worldScene.setName(request.getName());
        worldSceneRepository.findByName(worldScene.getName())
                .ifPresent(checkWorldScene -> {
                    throw new AppException(ErrorCode.WORLD_SCENE_ALREADY_EXISTED);
                });
        try {
            worldScene = worldSceneRepository.save(worldScene);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return worldSceneMapper.toWorldSceneResponse(worldScene);
    }

    public void deleteWorldScene(String worldSceneId) {
        WorldScene worldScene = worldSceneRepository.findById(worldSceneId)
                .orElseThrow(() -> new AppException(ErrorCode.WORLD_SCENE_NOT_FOUND));
        worldScene.setIsDeleted(true);
        try {
            worldSceneRepository.save(worldScene);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public List<WorldSceneResponse> getWorldSceneByName(String name) {
        return worldSceneRepository.findByName(name).stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .collect(Collectors.toList());
    }

    public List<WorldSceneResponse> getActiveWorldScene() {
        return worldSceneRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .collect(Collectors.toList());
    }
}
