package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.WorldScene;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorldSceneRepository extends JpaRepository<WorldScene, String> {
    boolean existsByName(String name);
    Optional<WorldScene> findByName(String name);
    List<WorldScene> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);

    Optional<WorldScene> findByIdIn(List<String> worldSceneIds);

    List<WorldScene> findAllByIsActiveAndIsDeletedOrderByName(boolean b, boolean b1);
}
