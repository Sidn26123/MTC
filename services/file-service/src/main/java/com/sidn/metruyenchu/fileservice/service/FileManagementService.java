package com.sidn.metruyenchu.fileservice.service;

import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementCreateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.StoredFile.FileManagementResponse;
import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import com.sidn.metruyenchu.fileservice.exception.AppException;
import com.sidn.metruyenchu.fileservice.dto.PageResponse;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.GetAllFileManagementRequest;
import com.sidn.metruyenchu.fileservice.exception.ErrorCode;
import com.sidn.metruyenchu.fileservice.mapper.FileManagementMapper;
import com.sidn.metruyenchu.fileservice.repository.FileManagementRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sidn.metruyenchu.fileservice.utils.FuncUtils.getPageable;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileManagementService {

    FileManagementRepository fileManagementRepository;
    FileManagementMapper fileManagementMapper;
    /**
     * Lưu một file mới vào database
     */
    public FileManagementResponse saveFileAndRes(FileManagementCreateRequest request) {
        FileManagement fileManagement = saveFile(request);
        return fileManagementMapper.toFileManagementResponse(fileManagement);
    }

    public FileManagement saveFile(FileManagementCreateRequest request) {
        FileManagement fileManagement = FileManagement.builder()
                .fileName(request.getFileName())
                .displayName(request.getDisplayName())
                .path(request.getPath())
                .relativePath(request.getRelativePath())
                .fileExtension(request.getExtension())
                .isDeleted(false)
                .build();

        return fileManagementRepository.save(fileManagement);
    }

    /**
     * Lấy danh sách tất cả file chưa bị xóa
     */
    public PageResponse<FileManagementResponse> getStoredFiles(GetAllFileManagementRequest request) {
//        Pageable pageable = getPageable();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPageRequest().getPage() - 1, request.getPageRequest().getSize(), sort);
        Page<FileManagement> storedFiles = fileManagementRepository.findAllByIsDeleted(request.getIsDeleted(), pageable);
        List<FileManagementResponse> fileManagementRespons = storedFiles.stream()
                .map(fileManagementMapper::toFileManagementResponse)
                .toList();

        return PageResponse.<FileManagementResponse>builder()
                .data(fileManagementRespons)
                .totalElements(storedFiles.getTotalElements())
                .totalPages(storedFiles.getTotalPages())
                .currentPage(request.getPageRequest().getPage())
                .pageSize(request.getPageRequest().getSize())
                .build();
    }


    public FileManagementResponse getStoredFile(String id) {
        return fileManagementMapper.toFileManagementResponse(fileManagementRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND)));
    }



    /**
     * Xóa mềm file (chỉ đánh dấu isDeleted = true)
     */
    public Void softDeleteFile(String id) {
        FileManagement fileManagement = fileManagementRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        fileManagement.setIsDeleted(true);
        fileManagementRepository.save(fileManagement);

        return null;
    }

    public Void deleteFile(String id) {
        fileManagementRepository.deleteById(id);
        return null;
    }

    public FileManagementResponse updateFile(String id, FileManagementUpdateRequest request) {
        FileManagement fileManagement = fileManagementRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        fileManagementMapper.updateFileManagement(fileManagement, request);
        FileManagement updatedFile = fileManagementRepository.save(fileManagement);
        return fileManagementMapper.toFileManagementResponse(updatedFile);
    }




}
