package com.sidn.metruyenchu.fileservice.repository;

import com.sidn.metruyenchu.fileservice.dto.FileInfo;
import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileRepository {
    @Value("${file.storage-dir}")
    String storageDir;

    @Value("${file.download-prefix}")
    String urlPrefix;

    @Value("${file.media}")
    String mediaPartDir;
    @Value("${file.novel}")
    String novelPartDir;
    public FileInfo store(MultipartFile file, String path) throws IOException {
        Path folder = Paths.get(path);

        String fileExtension = StringUtils
                .getFilenameExtension(file.getOriginalFilename());

        String fileName = Objects.isNull(fileExtension)
                ? UUID.randomUUID().toString()
                : UUID.randomUUID() + "." + fileExtension;

        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return FileInfo.builder()
                .fileName(fileName)
                .size(file.getSize())
                .contentType(file.getContentType())
                .md5Checksum(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .path(filePath.toString())
                .url(urlPrefix + fileName)
                .build();
    }
    public FileInfo store(MultipartFile file) throws IOException {
        return store(file, getMediaDir());
    }

    public FileInfo store(MultipartFile file, int locationChoice) throws IOException {
        if (locationChoice == 0){
            return store(file, getMediaDir());
        } else {
            return store(file, getNovelDir());
        }
    }
    public Resource read(FileManagement fileMgmt) throws IOException {
        var data = Files.readAllBytes(Path.of(fileMgmt.getPath()));
        return new ByteArrayResource(data);
    }

    public String getMediaDir(){
        return storageDir + "/" + mediaPartDir;
    }

    public String getNovelDir(){
        return storageDir + "/" + novelPartDir;
    }



}
