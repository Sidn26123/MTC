package com.sidn.metruyenchu.fileservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sidn.metruyenchu.fileservice.dto.response.CloudinaryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName){
        String extension = FilenameUtils.getExtension(fileName); // Lấy extension
        String baseName = FilenameUtils.getBaseName(fileName); // Lấy tên file không có extension
        String publicId = baseName + "_" + System.currentTimeMillis();
        Map mapObj = ObjectUtils.asMap("public_id", publicId);

        if (extension.matches("jpg|jpeg|png|gif|bmp|webp")) {
            publicId += ".jpg";

        } else if (extension.matches("txt")) {
            publicId += ".txt";
            mapObj = ObjectUtils.asMap("public_id", publicId, "resource_type", "raw");
        } else {
            throw new IllegalArgumentException("File không hợp lệ");
        }

        try {
            final Map result = cloudinary.uploader()
                    .upload(file.getBytes(), mapObj);
            final String url = (String) result.get("url");
            publicId = (String) result.get("public_id");
            log.info("File uploaded successfully");
            log.info("Public id: {}", publicId);
            return CloudinaryResponse.builder()
                    .publicId(publicId)
                    .url(url)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading file");
        }
    }

    public CloudinaryResponse prepareStorageForNovel(String novelId) {
        String baseFolder = "NovelData/novel_" + novelId;
        String metadataPath = baseFolder + "/metadata.json";
        String imagePlaceholder = baseFolder + "/images/_placeholder.txt";
        String dataPlaceholder = baseFolder + "/datas/_placeholder.txt";

        try {
            // 1. Tạo metadata.json
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("novel_id", novelId);
            metadata.put("created_at", System.currentTimeMillis());

            cloudinary.uploader().upload(metadata.toString().getBytes(),
                    ObjectUtils.asMap("folder", baseFolder, "resource_type", "raw"));
            cloudinary.uploader().upload(metadata.toString().getBytes(),
                    ObjectUtils.asMap("public_id", metadataPath, "resource_type", "raw"));
            // 2. Đọc placeholder từ resources/static
            byte[] placeholderBytes = readResourceFile("static/_placeholder.txt");
//            Map uploadResult = cloudinary.uploader().upload(
//                    placeholderBytes,
//                    ObjectUtils.asMap("folder", "ancs")
//            );
            // 3. Tạo folder images bằng cách upload file placeholder.txt
            cloudinary.uploader().upload(placeholderBytes,
                    ObjectUtils.asMap("folder", imagePlaceholder, "resource_type", "raw"));

            // 4. Tạo folder datas bằng cách upload file placeholder.txt
            cloudinary.uploader().upload(placeholderBytes,
                    ObjectUtils.asMap("folder", dataPlaceholder, "resource_type", "raw"));

            // Trả về response
            return CloudinaryResponse.builder()
                    .publicId(baseFolder)
                    .url(cloudinary.url().generate(baseFolder))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] readResourceFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        Path path = resource.getFile().toPath();
        return Files.readAllBytes(path);
    }

}
