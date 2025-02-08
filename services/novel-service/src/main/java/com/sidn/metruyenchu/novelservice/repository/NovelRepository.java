package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, String> {
    Novel findByName(String name);

    Novel findBySlug(String slug);

}
