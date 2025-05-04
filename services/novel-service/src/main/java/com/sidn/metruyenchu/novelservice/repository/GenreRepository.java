package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    boolean existsByName(String name);
    Optional<Genre> findByName(String name);
    List<Genre> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);
    List<Genre> findAllByIsActiveAndIsDeletedOrderByName(Boolean isActive, Boolean isDeleted);

    Optional<Genre> findByIdIn(List<String> genreIds);
}
