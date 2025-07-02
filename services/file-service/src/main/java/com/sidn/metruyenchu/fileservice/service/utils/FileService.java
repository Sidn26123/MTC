package com.sidn.metruyenchu.fileservice.service.utils;

import com.sidn.metruyenchu.fileservice.dto.FileInfo;
import com.sidn.metruyenchu.fileservice.dto.response.FileResponse;
import com.sidn.metruyenchu.fileservice.dto.response.StoredFile.FileManagementResponse;
import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import com.sidn.metruyenchu.fileservice.exception.AppException;
import com.sidn.metruyenchu.fileservice.exception.ErrorCode;
import com.sidn.metruyenchu.fileservice.mapper.FileManagementMapper;
import com.sidn.metruyenchu.fileservice.repository.FileManagementRepository;
import com.sidn.metruyenchu.fileservice.repository.FileRepository;
import com.sidn.metruyenchu.fileservice.repository.NovelFileManagementRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileService {

    @NonFinal
    @Value("${file.upload-dir}")
    String uploadDir;

    @NonFinal
    @Value("${file.storage-dir}")
    String storageDir;


    FileRepository fileRepository;
    NovelFileManagementRepository novelFileManagementRepository;
    FileManagementRepository fileManagementRepository;
    FileManagementMapper fileManagementMapper;


    public FileResponse uploadFile(MultipartFile file) throws IOException {
        // Store file
        FileInfo fileInfo = fileRepository.store(file);

        // Create file management info
        FileManagement fileMgmt = fileManagementMapper.toFileManagement(fileInfo);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        fileMgmt.setOwnerId(userId);

        fileMgmt = fileManagementRepository.save(fileMgmt);
        return FileResponse.builder()
                .originalFileName(file.getOriginalFilename())
                .url(fileInfo.getUrl())
                .build();
    }

    public FileManagement writeFile(MultipartFile file, String path) throws IOException {
        // Store file
        log.info("Log");
        FileInfo fileInfo = fileRepository.store(file, path);

        // Create file management info
        FileManagement fileMgmt = fileManagementMapper.toFileManagement(fileInfo);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        fileMgmt.setOwnerId(userId);

        fileMgmt = fileManagementRepository.save(fileMgmt);
        return fileMgmt;
    }

    public FileManagement writeFile(String content, String path) throws IOException {
        // Store file
        Path folder = Paths.get(path);
        String fileName = UUID.randomUUID().toString() + ".txt";
        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();
        Files.writeString(filePath, content);

        // Create file management info
        FileInfo fileInfo = FileInfo.builder()
                .fileName(fileName)
                .path(filePath.toString())
                .build();

        FileManagement fileMgmt = fileManagementMapper.toFileManagement(fileInfo);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        fileMgmt.setOwnerId(userId);
        fileMgmt.setDisplayName(fileName);

        fileMgmt = fileManagementRepository.save(fileMgmt);
        return fileMgmt;
    }

    public Resource downloadFile(String fileId) throws IOException {
        var fileMgmt = fileManagementRepository.findByFileName(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        return fileRepository.read(fileMgmt);
    }

    public FileManagementResponse updateFile(String fileId, MultipartFile file) throws IOException {
        var fileMgmt = fileManagementRepository.findByFileName(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        // Delete old file
        deleteFileInDestination(fileMgmt.getPath());

        // Store new file
        var fileInfo = fileRepository.store(file);
        fileMgmt.setPath(fileInfo.getPath());
        fileMgmt.setFileName(fileInfo.getFileName());
        fileMgmt.setDisplayName(file.getOriginalFilename());
        return fileManagementMapper.toFileManagementResponse(fileManagementRepository.save(fileMgmt));
    }


    public String deleteFile(String fileName) throws IOException {
        FileManagement fileMgmt = fileManagementRepository.findByFileName(fileName)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        fileMgmt.setIsDeleted(true);
        fileManagementRepository.save(fileMgmt);
        return fileMgmt.getFileName();
    }

    public String completeDeleteFile(String fileId) throws IOException {
        FileManagement fileMgmt = fileManagementRepository.findByFileName(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        deleteFileInDestination(fileMgmt.getPath());
        fileManagementRepository.delete(fileMgmt);
        return fileMgmt.getFileName() + " deleted completed";
    }
    public void writeFileToDisk(String content, String path) throws IOException {
        Path filePath = Paths.get(path);
        Files.writeString(filePath, content);
    }
    public FileManagement getFile(String fileId) {
        return fileManagementRepository.findByFileName(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
    }

    public Resource getContentOfFileManagement(FileManagement fileManagement) throws IOException {
        Path path = Paths.get(fileManagement.getPath());
        return fileRepository.read(fileManagement);
    }

    //delete file in destination with file path
    public void deleteFileInDestination(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }

    public String getNovelsUploadPath(){
        return Paths.get(storageDir, "novels").toString();
    }

    public String getUserUploadsPath(){
        return Paths.get(storageDir, "user-uploads").toString();
    }

    public String getChapterContent(String chapterId){
        FileManagement fileMgt = novelFileManagementRepository.findByChapterId(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND))
                .getFile();

        String fileName = fileMgt.getFileName();
        String filePath = fileMgt.getPath();

        Path path = Paths.get(filePath);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            log.error("Error reading file: {}", fileName, e);
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

    }


}
