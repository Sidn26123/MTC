package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, String> {
    boolean existsByName(String name);
    Optional<Genre> findByName(String name);
    List<Genre> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);
}
