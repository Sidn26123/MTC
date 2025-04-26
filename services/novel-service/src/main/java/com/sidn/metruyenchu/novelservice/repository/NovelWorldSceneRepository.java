package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import com.sidn.metruyenchu.novelservice.entity.NovelWorldScene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelWorldSceneRepository extends JpaRepository<NovelWorldScene, String> {
}
