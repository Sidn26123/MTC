package com.sidn.metruyenchu.fileservice.service;

import com.sidn.metruyenchu.fileservice.dto.PageResponse;
import com.sidn.metruyenchu.fileservice.dto.request.*;
import com.sidn.metruyenchu.fileservice.dto.response.FileResponse;
import com.sidn.metruyenchu.fileservice.dto.response.NovelFileResponse;
import com.sidn.metruyenchu.fileservice.entity.NovelFile;
import com.sidn.metruyenchu.fileservice.enums.FilePurposeEnum;
import com.sidn.metruyenchu.fileservice.exception.AppException;
import com.sidn.metruyenchu.fileservice.exception.ErrorCode;
import com.sidn.metruyenchu.fileservice.mapper.NovelFileMapper;
import com.sidn.metruyenchu.fileservice.repository.NovelFileRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileStorageService {
    NovelFileMapper novelFileMapper;

    @NonFinal
    @Value("${file.upload-dir}")
    String uploadDir;

    @NonFinal
    @Value("${file.novel-stogare-dir}")
    String novelStorageDir;

    NovelFileRepository novelFileRepository;


    /**
     * Tạo folder lưu trữ cho truyện
     * Gồm 2 folder: images và contents
     * Và file metadata.json
     * @param request
     * @return
     */
    public FileResponse prepareStorageForNovel(PrepareStorageRequest request) {
        String novelId = request.getNovelId();
        Path novelPath = Paths.get(uploadDir, novelId);
        Path imagesPath = novelPath.resolve("images");
        Path contentsPath = novelPath.resolve("contents");
        Path metadataPath = novelPath.resolve("metadata.json");
        if (Files.exists(novelPath)) {
            return FileResponse.builder()
                    .message("Folder đã tồn tại")
                    .path(novelPath.toString())
                    .build();
        }

        try {
            Files.createDirectories(imagesPath);
            Files.createDirectories(contentsPath);

            // Tạo file metadata.json
            File metadataFile = new File(metadataPath.toString());
            if (metadataFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(metadataFile)) {
                    writer.write("{\"novelId\": \"" + novelId + "\"}");
                }
            }

            return FileResponse.builder()
                    .message("Folder created successfully")
                    .path(novelPath.toString())
                    .build();
        } catch (IOException e) {
            return FileResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
        }

    }

    public NovelFileResponse uploadContent(ChapterUploadContentRequest request, MultipartFile file) throws IOException {
        String novelId = request.getNovelId();
        String chapterId = request.getChapterId();
        Path novelPath = Paths.get(uploadDir, novelId);
        Path contentsPath = novelPath.resolve("contents");
//        Path filePath = contentsPath.resolve(chapterId);
        String chapterPath = buildChapterPath(novelId, chapterId);
        if (!Files.exists(novelPath) || !Files.exists(contentsPath)) {
            throw new AppException(ErrorCode.FOLDER_NOT_FOUND);
        }

        Path path = Path.of(chapterPath);
        if (Files.exists(path)) {
            throw new AppException(ErrorCode.FILE_STORAGE_ERROR);
        }
//

        FileOutputStream fos = new FileOutputStream(path.toFile());
        fos.write(file.getBytes());
        fos.close();


        return novelFileMapper.toNovelFileResponse(
                novelFileRepository.save(novelFileMapper.toNovelFile(request)));

    }

    /**
     * Lấy file nội dung chương của truyện từ server
     * @return
     */
    public FileResponse getContentFileChapterOfNovel(ChapterContentGetRequest request) {
        String novelId = request.getNovelId();
        String chapterId = request.getChapterId();

//        Path novelPath = Paths.get(uploadDir, novelId);
//        Path contentsPath = novelPath.resolve("contents");
//        Path filePath = contentsPath.resolve(chapterId);
        String chapterPath = buildChapterPath(novelId, chapterId);
        Optional<NovelFile> novelFile = novelFileRepository.findByNovelIdAndChapterIdAndIsDeletedIsFalse(novelId, chapterId);
        //Kiểm tra file path dựa trên logic có còn hay k
        if (novelFile.isEmpty()) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        //Kiểm tra file có thực sự tồn tại không
        Path path = Paths.get(chapterPath);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        return FileResponse.builder()
                .message("File found")
                .path(chapterPath)
                .build();
    }

    @Transactional
    public String deleteChapterContent(ChapterContentGetRequest request) throws IOException {
        String novelId = request.getNovelId();
        String chapterId = request.getChapterId();
        String chapterPath = buildChapterPath(novelId, chapterId);

        Path path = Paths.get(chapterPath);
        if (!Files.exists(path)) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        Files.delete(path);
        Optional<NovelFile> novelFile = novelFileRepository.findByNovelIdAndChapterIdAndIsDeletedIsFalse(novelId, chapterId);
        NovelFileUpdateRequest updateRequest = NovelFileUpdateRequest.builder()
                .isDeleted(true)
                .build();
        if (novelFile.isPresent()) {
            novelFileMapper.updateNovelFile(
                    novelFile.get(),
                    updateRequest
            );
            log.info("isDeleted: {}", novelFile.get().getIsDeleted());
            novelFileRepository.save(novelFile.get());
        }


        return "Đã xoá file {} thành công";
    }

    public NovelFileResponse updateNovelFile(String chapterId, NovelFileUpdateRequest request) {
        NovelFile novelFile = novelFileRepository.findByChapterId(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        novelFileMapper.updateNovelFile(novelFile, request);
        return novelFileMapper.toNovelFileResponse(novelFileRepository.save(novelFile));
    }


    /**
     * Tải lên một tệp hình ảnh cho một chương cụ thể của tiểu thuyết.
     *
     * @param request yêu cầu chứa chi tiết của tiểu thuyết và chương
     * @param file tệp hình ảnh cần tải lên
     * @return phản hồi chứa chi tiết của tệp đã tải lên
     * @throws IOException nếu xảy ra lỗi I/O trong quá trình tải lên tệp
     * @throws AppException nếu thư mục tiểu thuyết hoặc thư mục hình ảnh không tồn tại, hoặc nếu tệp đã tồn tại
     */
    public NovelFileResponse uploadImage(ChapterUploadContentRequest request, MultipartFile file) throws IOException {
        String novelId = request.getNovelId();
        String chapterId = request.getChapterId();
        Path novelPath = Paths.get(uploadDir, novelId);
//        Path imagesPath = novelPath.resolve("images");
        String imagesPath = buildImagePath(novelId, chapterId, file.getOriginalFilename());
        log.info("imagesPath: {}", imagesPath);
        // Kiểm tra xem thư mục tiểu thuyết hoặc thư mục hình ảnh có tồn tại không
        Path pathImage = Path.of(imagesPath);
        if (!Files.exists(novelPath) || !Files.exists(novelPath.resolve("images"))) {
            throw new AppException(ErrorCode.FOLDER_NOT_FOUND);
        }


        // Kiểm tra xem tệp đã tồn tại chưa
        if (Files.exists(pathImage)) {
            log.info("File đã tồn tại");
            throw new AppException(ErrorCode.FILE_STORAGE_ERROR);
        }

        // Ghi tệp vào đường dẫn được chỉ định
        FileOutputStream fos = new FileOutputStream(pathImage.toFile());
        fos.write(file.getBytes());
        fos.close();

        // Lưu chi tiết tệp vào kho lưu trữ và trả về phản hồi
        return novelFileMapper.toNovelFileResponse(
                novelFileRepository.save(novelFileMapper.toNovelFile(request)));
    }

    
    /**
     * Lấy danh sách file của novel
     * @param request
     * @return PageResponse.NovelFileResponse
     */
    public PageResponse<NovelFileResponse> getChaptersOfNovel(NovelFileDetailsGetRequest request){
        String novelId = request.getNovelId();
        Sort sort = Sort.by(Sort.Direction.DESC, "created_at");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Page<NovelFile> novelFiles = novelFileRepository.findAllByNovelIdAndIsDeletedIsFalse(request.getNovelId(), pageable);

        List<NovelFileResponse> novelFileResponses = novelFiles.map(novelFileMapper::toNovelFileResponse).stream().toList();

        return PageResponse.<NovelFileResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(novelFiles.getTotalPages())
                .totalElements(novelFiles.getTotalElements())
                .data(novelFileResponses)
                .build();

    }

    public NovelFileResponse getNovelCover(NovelCoverGetRequest request){
        NovelFile novelFile = novelFileRepository.findByNovelIdAndPurposeAndIsDeletedIsFalse(request.getNovelId(), FilePurposeEnum.COVER)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        return novelFileMapper.toNovelFileResponse(novelFile);
    }


    /**
     * Lấy đường dẫn file relative tu database
     * @param request
     * @return
     */
    private String getPath(FilePathGetRequest request){
        NovelFile novelFile = new NovelFile();
        if (request.getFileId() != null){
            novelFile = novelFileRepository.findById(request.getFileId())
                    .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        }
        else if (request.getChapterId() != null){
            novelFile = novelFileRepository.findByChapterId(request.getChapterId())
                    .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        }

        if (novelFile == null){
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }
        return getNovelStoragePath() + "/" + novelFile.getRelativePath() + "/" + novelFile.getFileName();
    }


    /**
     * Lay duong dan tu root toi folder novel data
     * @param
     * @return
     */
    private String getNovelStoragePath() {
        return novelStorageDir;
    }

    private String buildChapterPath(String novelId, String chapterId){
        return Paths.get(novelStorageDir, novelId, "contents", chapterId).toString() + ".txt";
    }

    private String buildImagePath(String novelId, String chapterId, String fileName) {
        return Paths.get(novelStorageDir, novelId, "images", fileName).toString();
    }

}
