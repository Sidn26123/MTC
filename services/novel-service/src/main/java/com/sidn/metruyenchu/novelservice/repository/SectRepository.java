package com.sidn.metruyenchu.novelservice.repository;

import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.Sect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectRepository extends JpaRepository<Sect, String> {
}
