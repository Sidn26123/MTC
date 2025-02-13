package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.NovelAuthor;
import com.sidn.metruyenchu.novelservice.entity.NovelWorldScene;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelWorldSceneRepository extends JpaRepository<NovelWorldScene, String> {
}
