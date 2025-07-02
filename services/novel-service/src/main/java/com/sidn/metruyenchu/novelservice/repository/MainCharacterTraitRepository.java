package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.MainCharacterTrait;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainCharacterTraitRepository extends JpaRepository<MainCharacterTrait, String> {
    boolean existsByName(String name);
    Optional<MainCharacterTrait> findByName(String name);
    List<MainCharacterTrait> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);

    Optional<MainCharacterTrait> findByIdIn(List<String> mainCharTraitIds);

    List<MainCharacterTrait> findAllByIsActiveAndIsDeletedOrderByName(boolean b, boolean b1);
}
