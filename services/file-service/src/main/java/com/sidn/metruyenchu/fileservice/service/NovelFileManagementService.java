package com.sidn.metruyenchu.fileservice.service;

import com.sidn.metruyenchu.fileservice.dto.request.ChapterContentUploadRequest;
import com.sidn.metruyenchu.fileservice.dto.request.NovelFileUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.request.PrepareStorageRequest;
import com.sidn.metruyenchu.fileservice.dto.request.StoredFile.FileManagementUpdateRequest;
import com.sidn.metruyenchu.fileservice.dto.response.FolderResponse;
import com.sidn.metruyenchu.fileservice.dto.response.NovelFileResponse;
import com.sidn.metruyenchu.fileservice.dto.response.StoredFile.FileManagementResponse;
import com.sidn.metruyenchu.fileservice.entity.ChapterContent;
import com.sidn.metruyenchu.fileservice.entity.FileManagement;
import com.sidn.metruyenchu.fileservice.entity.NovelFileManagement;
import com.sidn.metruyenchu.fileservice.enums.FileCategoryEnum;
import com.sidn.metruyenchu.fileservice.exception.AppException;
import com.sidn.metruyenchu.fileservice.exception.ErrorCode;
import com.sidn.metruyenchu.fileservice.mapper.ChapterContentMapper;
import com.sidn.metruyenchu.fileservice.mapper.FileManagementMapper;
import com.sidn.metruyenchu.fileservice.mapper.NovelFileMapper;
import com.sidn.metruyenchu.fileservice.repository.ChapterContentRepository;
import com.sidn.metruyenchu.fileservice.repository.FileRepository;
import com.sidn.metruyenchu.fileservice.repository.NovelFileManagementRepository;
import com.sidn.metruyenchu.fileservice.repository.FileManagementRepository;
import com.sidn.metruyenchu.fileservice.service.utils.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelFileManagementService {
    NovelFileMapper novelFileMapper;
    FileManagementMapper fileManagementMapper;
    ChapterContentMapper chapterContentMapper;
    FileService fileService;
    @NonFinal
    @Value("${file.upload-dir}")
    String uploadDir;

    @NonFinal
    @Value("${file.novel-stogare-dir}")
    String novelStorageDir;

    @NonFinal
    @Value("${file.media-storage-dir}")
    String mediaStorageDir;

    NovelFileManagementRepository novelFileManagementRepository;

    FileManagementRepository fileManagementRepository;

    FileManagementService fileManagementService;

    FileRepository fileRepository;

    ChapterContentRepository chapterContentRepository;
    //upload file

    /**
     * Tạo folder lưu trữ cho truyện
     * Gồm 2 folder: images và contents
     * Và file metadata.json
     * @param request
     * @return
     */
    public FolderResponse prepareStorageForNovel(PrepareStorageRequest request) {
        String novelId = request.getNovelId();
        Path novelPath = Paths.get(uploadDir, novelId);
        Path imagesPath = novelPath.resolve("images");
        Path contentsPath = novelPath.resolve("contents");
        Path metadataPath = novelPath.resolve("metadata.json");
        if (Files.exists(novelPath)) {
            throw new AppException(ErrorCode.FOLDER_ALREADY_EXISTS);
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

            return FolderResponse.builder()
                    .message("Folder created successfully")
                    .path(novelPath.toString())
                    .build();
        } catch (IOException e) {
            return FolderResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
        }

    }

    //CRUD chapter content
//    public NovelFileResponse createChapterContent(ChapterContentUploadRequest request) throws IOException {
//        FileManagement fileManagement = fileManagementService.saveFile(
//                FileManagementCreateRequest.builder()
//                        .fileName(removeExtension(request.getFile().getOriginalFilename()))
//                        .displayName(request.getFile().getOriginalFilename())
//                        .relativePath(getChapterRelativePath(request.getNovelId()))
//                        .extension(extractExtension(request.getFile().getOriginalFilename()))
//                        .build()
//        );
//
//        NovelFileManagement novelFileManagement = NovelFileManagement.builder()
//                .novelId(request.getNovelId())
//                .chapterId(request.getChapterId())
//                .file(fileManagement)
//                .build();
//
//        String chapterPath = getDesPathFromStoredFile(fileManagement);
//        log.info("chapterPath: {}", chapterPath);
//        writeFileToLocal(request.getFile(), Paths.get(chapterPath));
//        novelFileManagementRepository.save(novelFileManagement);
//
//        return novelFileMapper.toNovelFileResponse(novelFileManagement);
////        StoredFileResponse storedFileResponse = storedFileService.saveFile(
////                StoredFileCreateRequest.builder()
////                        .fileName(request.getFile().getOriginalFilename())
////                        .displayName(request.getFile().getOriginalFilename())
////                        .relativePath(getChapterRelativePath(request.getNovelId()))
////                        .extension(extractExtension(request.getFile().getOriginalFilename()))
////                        .build()
////        );
////
////        String chapterPath = buildChapterPath(request.getNovelId(), request.getChapterId());
////        Path path = Paths.get(chapterPath);
////        if (!Files.exists(path)) {
////            throw new AppException(ErrorCode.FOLDER_NOT_FOUND);
////        }
////
////        writeFileToLocal(request.getFile(), path);
////
////        return NovelFileResponse.builder()
////                .id(storedFileResponse.getId())
////                .novelId(request.getNovelId())
////                .chapterId(request.getChapterId())
////                .relativePath(storedFileResponse.getRelativePath())
////                .type(FileCategoryEnum.CHAPTER_CONTENT)
////                .build();
//    }

    //CRUD cover


    //update stored file info
    public FileManagementResponse updateStoredFile(String fileId, FileManagementUpdateRequest request) {
        FileManagement fileManagement = fileManagementRepository.findById(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        fileManagementMapper.updateFileManagement(fileManagement, request);

        return fileManagementMapper.toFileManagementResponse(
                fileManagementRepository.save(fileManagement)
        );
    }
    //update content file
    public NovelFileResponse updateChapterContent(String chapterId, MultipartFile file) throws IOException {
        NovelFileManagement novelFileManagement = novelFileManagementRepository.findByChapterId(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        String novelId = novelFileManagement.getNovelId();
        String storedFileId = novelFileManagement.getFile().getId();

        FileManagement fileManagement = fileManagementRepository.findById(storedFileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        String chapterPath = getDesPathFromStoredFile(fileManagement);

        writeFileToLocal(file, Paths.get(chapterPath));

        fileManagementRepository.save(fileManagement);

        return novelFileMapper.toNovelFileResponse(
                novelFileManagementRepository.save(novelFileManagement)
        );
    }

    //delete file
    public void deleteNovelFile(String fileId) throws IOException {
        FileManagement fileManagement = fileManagementRepository.findById(fileId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        String path = getDesPathFromStoredFile(fileManagement);
        Files.delete(Paths.get(path));
        fileManagementRepository.delete(fileManagement);
    }

    //update novel file info
    public NovelFileResponse updateNovelFile(String chapterId, NovelFileUpdateRequest request) {
        NovelFileManagement novelFileManagement = novelFileManagementRepository.findByChapterId(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
        novelFileMapper.updateNovelFile(novelFileManagement, request);
        return novelFileMapper.toNovelFileResponse(novelFileManagementRepository.save(novelFileManagement));
    }


    public NovelFileResponse uploadChapterContent(ChapterContentUploadRequest request) throws IOException {
//        FileResponse fileResponse = fileService.uploadFile(request.getFile());
        //check noi dung file hay k, neu khong co thi content -> file

        //write content to disk
        FileManagement fileManagement = fileService.writeFile(request.getContent(), fileRepository.getNovelDir());
        log.info("content: {}", request.getContent());
        //save file to db để có thể implement search func
        ChapterContent chapterContent = ChapterContent.builder()
                .content(request.getContent())
                .chapterId(request.getChapterId())
                .build();
        chapterContentRepository.save(chapterContent);


        NovelFileManagement novelFileManagement = NovelFileManagement.builder()
                .novelId(request.getNovelId())
                .chapterId(request.getChapterId())
                .file(fileManagement)
                .build();
        return novelFileMapper.toNovelFileResponse(
                novelFileManagementRepository.save(novelFileManagement)
        );
    }

    //get path

    //write file

    //write file to local
    public void writeFileToLocal(MultipartFile file, Path path) throws IOException {
        writeFileToLocal(file, path, false);
    }
    public void writeFileToLocal(MultipartFile file, Path path, boolean createFolder) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        log.info("Path: {}", path);


        // Ghi file
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    }


//
//    public String getCoverNovelImageFileId(String novelId){
//        List<NovelFileManagement> novelFileManagement = novelFileManagementRepository.findAllByNovelIdAndCategoryAndIsDeletedIsFalse(novelId, FileCategoryEnum.COVER);
//        if (novelFileManagement.isEmpty()){
//            throw new AppException(ErrorCode.FILE_NOT_FOUND);
//        }
//        return novelFileManagement.getFile().getId();
//
//    }

    //build path

    //build chapter path
    public String buildChapterPath(String novelId, String fileName){
        return Paths.get(novelStorageDir, novelId, "contents", fileName).toString() + ".txt";
    }

    //build cover image path
    public String buildCoverImagePath(String novelId, String fileName){
        return Paths.get(novelStorageDir, novelId, "images", fileName).toString();
    }

    public String getChapterRelativePath(String novelId){
        return Paths.get(novelId, "contents").toString();
    }

    //get abs path novel
    public String getAbsPathNovel(String novelId){
        return Paths.get(novelStorageDir, novelId).toString();
    }

    //get abs path chapter
    public String getAbsPathChapter(String novelId, String chapterId){
        return Paths.get(novelStorageDir, novelId, "contents", chapterId).toString();
    }

    public String getDesPathFromStoredFile(FileManagement fileManagement){
        return Paths.get(novelStorageDir, fileManagement.getPath()).toString();
    }

    public String getImageOfNovelRelativePath(String novelId){
        return Paths.get(uploadDir, novelId, "images").toString();
    }

    public String getChapterContentStoreLocation(){
        return getChapterContentStorePath().toString();
    }

    public Path getChapterContentStorePath(){
        return Paths.get(uploadDir, "novels");
    }

    public NovelFileResponse uploadNovelCover(String novelId, MultipartFile file) throws IOException {
        List<NovelFileManagement> novelFileManagements = novelFileManagementRepository.findAllByNovelIdAndCategoryAndIsDeletedIsFalse(novelId, FileCategoryEnum.COVER);
        if (!novelFileManagements.isEmpty()){
            throw new AppException(ErrorCode.FILE_ALREADY_EXISTS);
        }
        FileManagement fileManagement = fileService.writeFile(file, mediaStorageDir);

        NovelFileManagement novelFileManagement = NovelFileManagement.builder()
                .novelId(novelId)
                .category(FileCategoryEnum.COVER)
                .file(fileManagement)
                .build();

        return novelFileMapper.toNovelFileResponse(
                novelFileManagementRepository.save(novelFileManagement)
        );
    }


//
//    public FileResponse uploadFile(MultipartFile file, Path storePath, boolean encrypt) throws IOException {
//        Path path = Paths.get(storePath.toUri());
//        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
//        String fileName = Objects.isNull(fileExtension)
//                ? UUID.randomUUID().toString()
//                : UUID.randomUUID().toString() + fileExtension;
//        Path filePath = path.resolve(fileName).normalize().toAbsolutePath();
//        log.info("filePath: {}", filePath);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        return FileResponse.builder()
//                .message("File uploaded successfully")
//                .path(filePath.toString())
//                .fileName(fileName)
//                .extension(extractFilename(fileExtension))
//                .build();
//    }
//
//    public FileResponse uploadFile(MultipartFile file) throws IOException {
//        return uploadFile(file, null, false);
//    }
//
//
//    public NovelFileResponse uploadChapterContent(ChapterUploadContentRequest request, MultipartFile file) throws IOException {
//        String novelId = request.getNovelId();
//        String chapterId = request.getChapterId();
//        Path novelPath = Paths.get(uploadDir, novelId);
//        Path contentsPath = novelPath.resolve("contents");
//        log.info("contentsPath: {}", contentsPath);
////        Path filePath = contentsPath.resolve(chapterId);
////        String chapterPath = buildChapterPath(novelId, chapterId);
//        if (!Files.exists(novelPath) || !Files.exists(contentsPath)) {
//            throw new AppException(ErrorCode.FOLDER_NOT_FOUND);
//        }
//
//        Path path = contentsPath;
//
////        if (Files.exists(path)) {
////            throw new AppException(ErrorCode.FILE_STORAGE_ERROR);
////        }
////
//        FileResponse fileRes = uploadFile(file, path, false);
//        log.info("fileRes: {}", fileRes);
////        FileOutputStream fos = new FileOutputStream(path.toFile());
////        fos.write(file.getBytes());
////        fos.close();
//
//        request.setFileName(fileRes.getFileName());
//        NovelFile novelFile = novelFileMapper.toNovelFile(request);
//        log.info("novelFile: {}", novelFile);
////        novelFile.setRelativePath(novelId + "/contents");
////        novelFile.setPurpose(FileCategoryEnum.CHAPTER_CONTENT);
//        return novelFileMapper.toNovelFileResponse(
//                novelFileRepository.save(novelFile));
//
//    }
//
//    /**
//     * Lấy file nội dung chương của truyện từ server
//     * @return
//     */
//    public FileResponse getContentFileChapterOfNovel(ChapterContentGetRequest request) {
//        String novelId = request.getNovelId();
//        String chapterId = request.getChapterId();
//
////        Path novelPath = Paths.get(uploadDir, novelId);
////        Path contentsPath = novelPath.resolve("contents");
////        Path filePath = contentsPath.resolve(chapterId);
//        String chapterPath = buildChapterPath(novelId, chapterId);
//        Optional<NovelFile> novelFile = novelFileRepository.findByNovelIdAndChapterIdAndIsDeletedIsFalse(novelId, chapterId);
//        //Kiểm tra file path dựa trên logic có còn hay k
//        if (novelFile.isEmpty()) {
//            throw new AppException(ErrorCode.FILE_NOT_FOUND);
//        }
//
//        //Kiểm tra file có thực sự tồn tại không
//        Path path = Paths.get(chapterPath);
//        if (!Files.exists(path) || Files.isDirectory(path)) {
//            throw new AppException(ErrorCode.FILE_NOT_FOUND);
//        }
//
//        return FileResponse.builder()
//                .message("File found")
//                .path(chapterPath)
//                .build();
//    }
//
//    @Transactional
//    public String deleteChapterContent(ChapterContentGetRequest request) throws IOException {
//        String novelId = request.getNovelId();
//        String chapterId = request.getChapterId();
//        String chapterPath = buildChapterPath(novelId, chapterId);
//
//        Path path = Paths.get(chapterPath);
//        if (!Files.exists(path)) {
//            throw new AppException(ErrorCode.FILE_NOT_FOUND);
//        }
//
//        Files.delete(path);
//        Optional<NovelFile> novelFile = novelFileRepository.findByNovelIdAndChapterIdAndIsDeletedIsFalse(novelId, chapterId);
//        NovelFileUpdateRequest updateRequest = NovelFileUpdateRequest.builder()
//                .isDeleted(true)
//                .build();
//        if (novelFile.isPresent()) {
//            novelFileMapper.updateNovelFile(
//                    novelFile.get(),
//                    updateRequest
//            );
//            log.info("isDeleted: {}", novelFile.get().getIsDeleted());
//            novelFileRepository.save(novelFile.get());
//        }
//
//
//        return "Đã xoá file {} thành công";
//    }
//
//    @Transactional
//    public String deleteNovelFile(FileDeleteRequest request) throws IOException {
//        String fileId = request.getFileId();
//        NovelFile novelFile = novelFileRepository.findById(fileId)
//                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
////        String novelId = novelFile.getNovelId();
////        String chapterId = novelFile.getChapterId();
////        String chapterPath = buildChapterPath(novelId, chapterId);
////
////        Path path = Paths.get(chapterPath);
//        Path path = Paths.get(retrieveFileAbsPath(fileId));
//        if (!Files.exists(path)) {
//            throw new AppException(ErrorCode.FILE_NOT_FOUND);
//        }
//
//        Files.delete(path);
//        novelFile.setIsDeleted(true);
//        novelFileRepository.save(novelFile);
//
//        return String.format("Đã xoá file %s thành công", fileId);
//    }
//
//    public NovelFileResponse updateNovelFile(String chapterId, NovelFileUpdateRequest request) {
//        NovelFile novelFile = novelFileRepository.findByChapterId(chapterId)
//                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
//        novelFileMapper.updateNovelFile(novelFile, request);
//        return novelFileMapper.toNovelFileResponse(novelFileRepository.save(novelFile));
//    }
//
//
//    /**
//     * Tải lên một tệp hình ảnh cho một chương cụ thể của tiểu thuyết.
//     *
//     * @param request yêu cầu chứa chi tiết của tiểu thuyết và chương
//     * @param file tệp hình ảnh cần tải lên
//     * @return phản hồi chứa chi tiết của tệp đã tải lên
//     * @throws IOException nếu xảy ra lỗi I/O trong quá trình tải lên tệp
//     * @throws AppException nếu thư mục tiểu thuyết hoặc thư mục hình ảnh không tồn tại, hoặc nếu tệp đã tồn tại
//     */
//    public NovelFileResponse uploadImage(ChapterUploadContentRequest request, MultipartFile file) throws IOException {
//        String novelId = request.getNovelId();
//        String chapterId = request.getChapterId();
//        Path novelPath = Paths.get(uploadDir, novelId);
////        Path imagesPath = novelPath.resolve("images");
//        String imagesPath = buildImagePath(novelId, chapterId, file.getOriginalFilename());
//        log.info("imagesPath: {}", imagesPath);
//        // Kiểm tra xem thư mục tiểu thuyết hoặc thư mục hình ảnh có tồn tại không
//        Path pathImage = Path.of(imagesPath);
//        if (!Files.exists(novelPath) || !Files.exists(novelPath.resolve("images"))) {
//            throw new AppException(ErrorCode.FOLDER_NOT_FOUND);
//        }
//
//
//        // Kiểm tra xem tệp đã tồn tại chưa
//        if (Files.exists(pathImage)) {
//            log.info("File đã tồn tại");
//            throw new AppException(ErrorCode.FILE_STORAGE_ERROR);
//        }
//
//        // Ghi tệp vào đường dẫn được chỉ định
//        FileOutputStream fos = new FileOutputStream(pathImage.toFile());
//        fos.write(file.getBytes());
//        fos.close();
//
//        // Lưu chi tiết tệp vào kho lưu trữ và trả về phản hồi
//        return novelFileMapper.toNovelFileResponse(
//                novelFileRepository.save(novelFileMapper.toNovelFile(request)));
//    }
//
//
//    /**
//     * Lấy danh sách file của novel
//     * @param request
//     * @return PageResponse.NovelFileResponse
//     */
//    public PageResponse<NovelFileResponse> getChaptersOfNovel(NovelFileDetailsGetRequest request){
//        String novelId = request.getNovelId();
//        Sort sort = Sort.by(Sort.Direction.DESC, "created_at");
//        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
//
//        Page<NovelFile> novelFiles = novelFileRepository.findAllByNovelIdAndIsDeletedIsFalse(request.getNovelId(), pageable);
//
//        List<NovelFileResponse> novelFileResponses = novelFiles.map(novelFileMapper::toNovelFileResponse).stream().toList();
//
//        return PageResponse.<NovelFileResponse>builder()
//                .currentPage(request.getPage())
//                .pageSize(request.getSize())
//                .totalPages(novelFiles.getTotalPages())
//                .totalElements(novelFiles.getTotalElements())
//                .data(novelFileResponses)
//                .build();
//
//    }
//
//    public NovelFileResponse getNovelCover(NovelCoverGetRequest request){
//        NovelFile novelFile = novelFileRepository.findByNovelIdAndPurposeAndIsDeletedIsFalse(request.getNovelId(), FileCategoryEnum.COVER)
//                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
//
//        return novelFileMapper.toNovelFileResponse(novelFile);
//    }
//
//    /**
//     * Lấy đường dẫn file relative tu database
//     * @param request
//     * @return
//     */
//    private String getPath(FilePathGetRequest request){
//        NovelFile novelFile = new NovelFile();
//        if (request.getFileId() != null){
//            novelFile = novelFileRepository.findById(request.getFileId())
//                    .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
//        }
//        else if (request.getChapterId() != null){
//            novelFile = novelFileRepository.findByChapterId(request.getChapterId())
//                    .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
//        }
//
//        if (novelFile == null){
//            throw new AppException(ErrorCode.FILE_NOT_FOUND);
//        }
//        return getNovelStoragePath() + "/" + novelFile.getRelativePath() + "/" + novelFile.getFileName();
//    }
//
//    public NovelFileResponse getNovelFileByNovelIdAndChapterId(String novelId, String chapterId){
//        NovelFile novelFile = novelFileRepository.findByNovelIdAndChapterIdAndIsDeletedIsFalse(novelId, chapterId)
//                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
//        return novelFileMapper.toNovelFileResponse(novelFile);
//    }
//
//    public NovelFileResponse getNovelFileById(String fileId){
//        NovelFile novelFile = novelFileRepository.findById(fileId)
//                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
//        return novelFileMapper.toNovelFileResponse(novelFile);
//    }
//
//    /**
//     * Lay duong dan tu root toi folder novel data
//     * @param
//     * @return
//     */
//    private String getNovelStoragePath() {
//        return novelStorageDir;
//    }
//
//    private String buildChapterPath(String novelId, String chapterId){
//        return Paths.get(novelStorageDir, novelId, "contents", chapterId).toString() + ".txt";
//    }
//
//    private String buildImagePath(String novelId, String chapterId, String fileName) {
//        return Paths.get(novelStorageDir, novelId, "images", fileName).toString();
//    }
//
//    private String retrieveFileAbsPath(String fileId){
//        NovelFile novelFile = novelFileRepository.getReferenceById(fileId);
//        return Paths.get(novelStorageDir, novelFile.getRelativePath(), novelFile.getFileName()).toString();
//    }

}
