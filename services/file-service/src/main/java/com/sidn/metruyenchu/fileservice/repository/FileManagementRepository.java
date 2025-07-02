package com.sidn.metruyenchu.fileservice.repository;

import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileManagementRepository extends JpaRepository<FileManagement, String> {
    List<FileManagement> findByIsDeletedFalse(); // Lấy danh sách file chưa bị xóa
    Optional<FileManagement> findByIdAndIsDeletedFalse(String id); // Tìm file chưa bị xóa theo ID

    Page<FileManagement> findAllByIsDeleted(Boolean isDeleted, Pageable pageable); // Lấy danh sách file chưa bị xóa theo trang

    Optional<FileManagement> findByFileName(String fileName); // Tìm file theo tên

//    Page<StoredFile> search
}
