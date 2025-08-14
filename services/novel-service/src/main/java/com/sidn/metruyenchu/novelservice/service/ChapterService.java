package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemGetRequest;
import com.sidn.metruyenchu.novelservice.dto.request.bookshelfItem.BookShelfItemUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.*;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogCreateRequest;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.mongo.ReadingLogUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.bookshelfItem.BookShelfItemResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.*;
import com.sidn.metruyenchu.novelservice.entity.*;
import com.sidn.metruyenchu.novelservice.enums.ChapterState;
import com.sidn.metruyenchu.novelservice.enums.NovelVisibility;
//import com.sidn.metruyenchu.novelservice.exception.AppException;
//import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.shared_library.exceptions.AppException;
import com.sidn.metruyenchu.shared_library.exceptions.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.ChapterMapper;
import com.sidn.metruyenchu.novelservice.mapper.NovelMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatusDetailRepository;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatusRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.httpclient.FileClient;
import com.sidn.metruyenchu.novelservice.spectification.ChapterSpecification;
import com.sidn.metruyenchu.novelservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sidn.metruyenchu.novelservice.utils.NovelUtils.getWordCount;
import static com.sidn.metruyenchu.novelservice.utils.TokenUtils.getUserIdFromContext;


/**
 * Service quản lý các thao tác liên quan đến chapter.
 * Cung cấp các chức năng tạo, lấy, cập nhật và xóa chapter.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService {
    ChapterRepository chapterRepository;
    NovelRepository novelRepository;
//    NovelService novelService;
    ChapterMapper  chapterMapper;
    NovelMapper novelMapper;
    ChapterStatusRepository chapterStatusRepository;
    ChapterStatusDetailRepository chapterStatusDetailRepository;
    FileClient fileClient;
    BookShelfService bookShelfService;
    BookShelfItemService bookShelfItemService;
    ReadingLogService readingLogService;

    public ChapterResponse getChapter(String chapterId) {
        log.info("chapterId: {}", chapterId);
        return chapterMapper.toChapterResponse(
                chapterRepository.findById(chapterId).orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND))
        );
    }

    public PageResponse<ChapterResponse> getAllChapters(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        var pageData = chapterRepository.findAll(pageable);


        return PageUtils.toPageResponse(
                pageData,
                chapterMapper::toChapterResponse,
                page
        );

        //        return PageResponse.<ChapterResponse>builder()
//                .currentPage(page)
//                .pageSize(pageData.getSize())
//                .totalPages(pageData.getTotalPages())
//                .totalElements(pageData.getTotalElements())
//                .data(pageData.getContent().stream().map(chapterMapper::toChapterResponse).toList())
//                .build();
    }


    /**
     * Tạo một chương mới và liên kết nó với một tiểu thuyết.
     *
     * Phương thức này xử lý việc tạo một chương mới, bao gồm thiết lập mối quan hệ
     * với tiểu thuyết, quản lý trạng thái chương và cập nhật tổng số chương cho
     * tiểu thuyết liên quan. Nó hỗ trợ hai chế độ:
     * - Chế độ chèn: Cho phép chèn chương tại một vị trí cụ thể và đẩy các chương sau.
     * - Chế độ mặc định: Thêm chương vào cuối danh sách chương của tiểu thuyết.
     *
     * @param request Thông tin yêu cầu chứa chi tiết của chương cần tạo.
     * @return Thông tin phản hồi chứa chi tiết của chương vừa được tạo.
     * @throws AppException nếu không tìm thấy tiểu thuyết hoặc chương đã tồn tại.
     */
    @Transactional
    public ChapterResponse createChapter(ChapterCreationRequest request) {
        if (request.getPublisher() == null) {
            request.setPublisher(getUserIdFromContext());
        }
        if (request.getState() == null) {
            request.setState(ChapterState.CREATED);
        }

        if (request.getIsInsertMode() == null) {
            request.setIsInsertMode(false);
        }

        var chapter = chapterMapper.toChapter(request);

        // Lấy tiểu thuyết liên quan theo ID, ném ngoại lệ nếu không tìm thấy
        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        // Liên kết chương với tiểu thuyết
        chapter.setNovel(novel);

        // Lấy danh sách trạng thái chương dựa trên các ID được cung cấp
        List<ChapterStatus> chapterStatuses = chapterStatusRepository.findAllById(request.getChapterStatus());

        // Tạo các thực thể ChapterStatusDetail để thiết lập mối quan hệ nhiều-nhiều
        List<ChapterStatusDetail> chapterStatusDetails = new ArrayList<>();
        for (ChapterStatus chapterStatus : chapterStatuses) {
            ChapterStatusDetail chapterStatusDetail = ChapterStatusDetail.builder()
                    .chapter(chapter)
                    .chapterStatus(chapterStatus)
                    .build();
            chapterStatusDetails.add(chapterStatusDetail);
        }

        // Chuyển đổi ChapterStatusDetail thành ChapterStatusResponse
        List<ChapterStatusResponse> chapterStatusResponses = new ArrayList<>();
        for (ChapterStatusDetail chapterStatusDetail : chapterStatusDetails) {
            ChapterStatusResponse chapterStatusResponse = ChapterStatusResponse.builder()
                    .id(chapterStatusDetail.getChapterStatus().getId())
                    .name(chapterStatusDetail.getChapterStatus().getName())
                    .build();
            chapterStatusResponses.add(chapterStatusResponse);
        }

        // Thiết lập trạng thái chương cho chương
        chapter.setChapterStatus(chapterStatusDetails);
        chapter.setWordCount(request.getContent() != null ? getWordCount(request.getContent()) : 0);
        // Lấy tổng số chương hiện tại của tiểu thuyết
        Integer totalChapters = novel.getTotalChapters();
        log.info("Tổng số chương: {}", totalChapters);

        // Nếu không ở chế độ chèn, đặt chỉ số chương tăng thêm 1 từ tổng số chương
        if (!request.getIsInsertMode()) {
            chapter.setChapterIdx(totalChapters + 1);
        }

        // Nếu ở chế độ chèn, đặt chỉ số chương và đẩy các chương sau
        if (request.getIsInsertMode()) {
            chapter.setChapterIdx(request.getChapterIdx());
            chapterRepository.bulkUpdateChapterIdx(novel, request.getChapterIdx());
        }

        try {
            // Lưu chương vào kho dữ liệu
            chapter = chapterRepository.save(chapter);

            // Cập nhật tổng số chương cho tiểu thuyết
            novelRepository.updateTotalChapters(novel.getId(), totalChapters + 1);
        } catch (DataIntegrityViolationException exception) {
            // Ném ngoại lệ nếu chương đã tồn tại
            throw new AppException(ErrorCode.CHAPTER_ALREADY_EXISTS);
        }

        // Chuyển đổi chương đã lưu thành ChapterResponse
        ChapterResponse chapterResponse = chapterMapper.toChapterResponse(chapter);

        // Thiết lập trạng thái chương trong phản hồi
        chapterResponse.setChapterStatus(chapterStatusResponses);

        // Trả về phản hồi
        return chapterResponse;
    }

    @Transactional
    public List<ChapterResponse> createChapters(List<ChapterCreationRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }
        log.info("Creating {} chapters", requests.size());

        // Pre-fetch user ID and validate requests
        String publisher = getUserIdFromContext();
        List<ChapterResponse> responses = new ArrayList<>();
        Map<String, Novel> novelCache = new HashMap<>();
        Map<String, ChapterStatus> statusCache = new HashMap<>();

        // Group requests by novel to optimize processing
        Map<String, List<ChapterCreationRequest>> requestsByNovel = requests.stream()
                .collect(Collectors.groupingBy(ChapterCreationRequest::getNovelId));

        for (Map.Entry<String, List<ChapterCreationRequest>> entry : requestsByNovel.entrySet()) {
            String novelId = entry.getKey();
            List<ChapterCreationRequest> novelRequests = entry.getValue();

            // Fetch novel once per novel ID
            Novel novel = novelCache.computeIfAbsent(novelId, id -> novelRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND)));

            // Fetch all required chapter statuses in one query
            Set<String> statusIds = novelRequests.stream()
                    .flatMap(req -> req.getChapterStatus().stream())
                    .collect(Collectors.toSet());
            List<ChapterStatus> chapterStatuses = chapterStatusRepository.findAllById(statusIds);
            statusCache.putAll(chapterStatuses.stream()
                    .collect(Collectors.toMap(ChapterStatus::getId, Function.identity())));

            // Prepare chapters and status details
            List<Chapter> chapters = new ArrayList<>();
            List<ChapterStatusDetail> allStatusDetails = new ArrayList<>();
            int totalChapters = novel.getTotalChapters();

            for (ChapterCreationRequest request : novelRequests) {
                // Set defaults
                if (request.getPublisher() == null) {
                    request.setPublisher(publisher);
                }
                if (request.getState() == null) {
                    request.setState(ChapterState.CREATED);
                }
                if (request.getIsInsertMode() == null) {
                    request.setIsInsertMode(false);
                }

                // Map request to chapter
                Chapter chapter = chapterMapper.toChapter(request);
                chapter.setNovel(novel);

                // Handle chapter index
                if (!request.getIsInsertMode()) {
                    chapter.setChapterIdx(++totalChapters);
                } else {
                    chapter.setChapterIdx(request.getChapterIdx());
                }

                // Create chapter status details
                List<ChapterStatusDetail> statusDetails = request.getChapterStatus().stream()
                        .map(statusId -> {
                            ChapterStatus status = statusCache.get(statusId);
                            if (status == null) {
                                throw new AppException(ErrorCode.CHAPTER_STATUS_NOT_FOUND);
                            }
                            return ChapterStatusDetail.builder()
                                    .chapter(chapter)
                                    .chapterStatus(status)
                                    .build();
                        })
                        .collect(Collectors.toList());

                chapter.setChapterStatus(statusDetails);
                chapters.add(chapter);
                allStatusDetails.addAll(statusDetails);
            }

            // Handle insert mode: bulk update chapter indices
            novelRequests.stream()
                    .filter(ChapterCreationRequest::getIsInsertMode)
                    .forEach(req -> chapterRepository.bulkUpdateChapterIdx(novel, req.getChapterIdx()));

            try {
                // Batch save chapters and status details
                chapterRepository.saveAll(chapters);
                chapterStatusDetailRepository.saveAll(allStatusDetails);

                // Update total chapters for novel
                novelRepository.updateTotalChapters(novel.getId(), totalChapters);
            } catch (DataIntegrityViolationException e) {
                throw new AppException(ErrorCode.CHAPTER_ALREADY_EXISTS);
            }

            // Map to responses
            log.info("Chapters created for novel: {}", chapters.size());
            log.info("Novel ID: {}", chapters.getFirst().getId());
            for (Chapter chapter : chapters) {
                ChapterResponse response = chapterMapper.toChapterResponse(chapter);
                List<ChapterStatusResponse> statusResponses = chapter.getChapterStatus().stream()
                        .map(detail -> ChapterStatusResponse.builder()
                                .id(detail.getChapterStatus().getId())
                                .name(detail.getChapterStatus().getName())
                                .build())
                        .collect(Collectors.toList());
                response.setChapterStatus(statusResponses);
                responses.add(response);
            }
        }

        return responses;
    }
    /**
     * Xóa một chương theo ID.
     * @param chapterId
     */
    public void deleteChapter(String chapterId) {
        if (!chapterRepository.existsById(chapterId)) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        chapterRepository.deleteById(chapterId);
    }

    /**
     * Update một chương theo ID.
     * @param chapterId
     * @param request
     * @return
     */
    public ChapterResponse updateChapter(String chapterId, ChapterUpdateRequest request) {
        var chapter = chapterRepository.findById(chapterId).
                orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        log.info("Chapter ID: {}", request);
        chapterMapper.update(chapter, request);

        if (request.getContent() != null) {
            chapter.setWordCount(getWordCount(request.getContent()));
            log.info("Word count: {}", getWordCount(request.getContent()));
        }
//        chapterMapper.updateChapterFromRequest(chapter, request);
        try {
            chapter = chapterRepository.save(chapter);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.CHAPTER_ALREADY_EXISTS);
        }

        return chapterMapper.toChapterResponse(chapter);
    }




    /**
     * Lấy danh sách các chương theo ID tiểu thuyết.
     *
     * @param request Thông tin yêu cầu chứa ID tiểu thuyết và thông tin phân trang.
     * @return Danh sách các chương liên quan đến tiểu thuyết.
     */
    public PageResponse<ChapterResponse> getChaptersByNovelId(ChaptersOfNovelGetRequest request) {
        Novel novel = novelRepository.findById(request.getNovelId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
//        return chapterRepository.findByNovel(novel)
//                .stream()
//                .map(chapterMapper::toChapterResponse)
//                .toList();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        var pageData = chapterRepository.findByNovelId(request.getNovelId(), pageable);


        return PageResponse.<ChapterResponse>builder()
                .currentPage(request.getPage())
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(chapterMapper::toChapterResponse).toList())
                .build();
    }

    /**
     * Lấy danh sách các chương theo ID tiểu thuyết.
     *
     * @param request Thông tin yêu cầu chứa ID tiểu thuyết và thông tin phân trang.
     * @return Danh sách các chương liên quan đến tiểu thuyết.
     */
    public PageResponse<ChapterListResponse> getChapterList(ChapterListGetRequest request){
        Novel novel = new Novel();

        if (request.getNovelId() != null){
            novel = novelRepository.findById(request.getNovelId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        }
        else if (request.getNovelSlug() != null){
            novel = novelRepository.findBySlug(request.getNovelSlug())
                    .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        }
        else {
            throw new AppException(ErrorCode.NOVEL_NOT_FOUND);
        }
        log.info("Novel: {}", novel.getId());

        Pageable pageable = PageUtils.from(request);

//        var pageData = chapterRepository.findByNovelId(request.getNovelId(), pageable);


        var pageData = chapterRepository.findByNovel(novel, pageable);
        return PageUtils.toPageResponse(
                pageData,
                chapterMapper::toChapterListResponse,
                request.getPage()

        );
    }

    /**
     * Tạo mối quan hệ nhiều-nhiều giữa chương và trạng thái chương.
     * @param chapter
     * @param chapterStatuses
     * @return
     */
    private List<ChapterStatusDetail> createManyRelation(Chapter chapter, List<ChapterStatus> chapterStatuses) {
        List<ChapterStatusDetail> chapterStatusDetails = new ArrayList<>();
        for (ChapterStatus chapterStatus : chapterStatuses) {
            ChapterStatusDetail chapterStatusDetail = ChapterStatusDetail.builder()
                    .chapter(chapter)
                    .chapterStatus(chapterStatus)
                    .build();
            chapterStatusDetails.add(chapterStatusDetail);
        }
        return chapterStatusDetails;
    }

    private ChapterResponse fillManyRelationResponse(ChapterResponse response, List<String> chapterStatusIds) {
        List<ChapterStatusResponse> chapterStatusResponses = new ArrayList<>();
        for (String chapterStatusId : chapterStatusIds) {
            ChapterStatusResponse chapterStatusResponse = ChapterStatusResponse.builder()
                    .id(chapterStatusId)
                    .build();
            chapterStatusResponses.add(chapterStatusResponse);
        }

        response.setChapterStatus(chapterStatusResponses);

        return response;
    }

    private List<ChapterStatus> getChapterStatus (List<ChapterStatusDetail> chapterStatusDetails) {
        List<ChapterStatus> chapterStatuses = new ArrayList<>();
        for (ChapterStatusDetail chapterStatusDetail : chapterStatusDetails) {
            chapterStatuses.add(chapterStatusDetail.getChapterStatus());
        }

        return chapterStatuses;
    }

    public String getRawChapterContent(ChapterContentGetRequest request) {
        String content = fileClient.getChapterContent(request.getChapterId());


        return content;
    }

    public ChapterContentResponse getChapterContent(ChapterContentGetRequest request) {
        Chapter chapter = chapterRepository.findByNovelSlugAndChapterIdxAndIsDeletedIsFalse(
                request.getNovelSlug(),
                request.getChapterIdx()
        ).orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        String userId = getUserIdFromContext();

        checkUserCanReadChapterThrow(chapter, userId);

        

        return chapterMapper.toChapterContentResponse(chapter);
    }
    public ChapterContentResponse getChapterContentById(ChapterContentGetRequest request) {
        Chapter chapter = chapterRepository.findById(
                request.getChapterId()
        ).orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        String userId = getUserIdFromContext();

        checkUserCanReadChapterThrow(chapter, userId);



        return chapterMapper.toChapterContentResponse(chapter);
    }

    /**
     * Kiểm tra xem một chương có phù hợp các điều kiện để xuất bản hay không.
     * @param chapterId
     * @return
     */
    public ChapterPublishCheckResponse checkChapterSuitForPublish(String chapterId) {
        Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        if (chapter.getContent().length() < 100) {
            return ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Nội dung chương phải lớn hơn 100 ký tự")
                    .suitable(false)
                    .build();
        }else if (chapter.getName().length() < 10) {
            return ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Tiêu đề chương phải lớn hơn 10 ký tự")
                    .suitable(false)
                    .build();
        }
        return ChapterPublishCheckResponse.builder()
                .chapterId(chapter.getId())
                .suitable(true)
                .build();
    }

    public List<ChapterPublishCheckResponse> checkChaptersSuitForPublishById(List<String> chapterIds) {
        List<ChapterPublishCheckResponse> responses = new ArrayList<>();
        for (String chapterId : chapterIds) {
            Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(chapterId)
                    .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

            checkContentChapterConstrains(responses, chapter);
        }
        return responses;
    }

    public List<ChapterPublishCheckResponse> checkChaptersSuitForPublish(List<Chapter> chapters){
        List<ChapterPublishCheckResponse> responses = new ArrayList<>();
        for (Chapter chapter : chapters) {
            checkContentChapterConstrains(responses, chapter);
        }
        return responses;
    }

    /**
     * Kiểm tra các ràng buộc nội dung của chương.
     * @param responses
     * @param chapter
     */
    private void checkContentChapterConstrains(List<ChapterPublishCheckResponse> responses, Chapter chapter) {
        if (chapter.getContent().length() < 10) {
            responses.add(ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Nội dung chương phải lớn hơn 10 ký tự")
                    .suitable(false)
                    .build());
        }else if (chapter.getName().length() < 10) {
            responses.add(ChapterPublishCheckResponse.builder()
                    .chapterId(chapter.getId())
                    .errorMessage("Tiêu đề chương phải lớn hơn 10 ký tự")
                    .suitable(false)
                    .build());
        }
    }

    public int getTotalChaptersByNovelId(String novelId) {
        return chapterRepository.countByNovelId(novelId);
    }

    /**
     * Kiểm tra user có tể truy cập nội dung chương hay không
     */
    public boolean checkUserCanReadChapter(CanUserReadChapterCheckRequest request){
        if (request.getUserId() == null){
            request.setUserId(getUserIdFromContext());
        }
        Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(request.getChapterId())
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        boolean canRead = true;
        //Nếu là tác giả thì có thể đọc
        if (chapter.getNovel().getAuthor().getId().equals(request.getUserId())) {
            return true;
        }
        //Nếu public thì có thể đọc
        if (chapter.getNovel().getNovelVisibility() == NovelVisibility.PUBLIC && chapter.getIsPublished()) {
            return true;
        }

        return false;
    }

    public boolean checkUserCanReadChapterThrow(Chapter chapter, String userId) {
        log.info("user: {}", userId);
        boolean canRead = false;
        log.info(String.valueOf(chapter.getNovel().getNovelVisibility()));
        //Nếu public thì có thể đọc
        if (chapter.getNovel().getNovelVisibility() == NovelVisibility.PUBLIC) {
            return true;
        }

        //Nếu là tác giả thì có thể đọc
        if (chapter.getNovel().getCurrentPublisher().equals(userId)) {
            return true;
        }

        throw new AppException(ErrorCode.USER_NOT_HAVE_PERMISSION);
    }

    public boolean checkNextChapterCanRead(Chapter chapter){
        Novel novel = chapter.getNovel();
        if (novel.getTotalChapters() <= chapter.getChapterIdx()) {
            return false; // Không có chương tiếp theo
        }

        Chapter nextChapter = chapterRepository.findByNovelAndChapterIdxAndIsDeletedIsFalse(
                novel,
                chapter.getChapterIdx() + 1
        ).orElse(null);
        
        return true;
    }

    public Chapter getNextChapterCanRead(Chapter chapter) {
        Novel novel = chapter.getNovel();
        if (novel.getTotalChapters() <= chapter.getChapterIdx()) {
            return null; // Không có chương tiếp theo
        }

        return chapterRepository.findByNovelAndChapterIdxAndIsDeletedIsFalse(
                novel,
                chapter.getChapterIdx() + 1
        ).orElse(null);
    }


    public boolean checkUserCanReadChapterThrow(Chapter chapter) {
        String userId = getUserIdFromContext();
        return checkUserCanReadChapterThrow(chapter, userId);
    }

    /**
     * Lọc các chương theo các điều kiện trong ChapterFilterRequest.
     * @param request ChapterFilterRequest
     * @return Danh sách các chương đã lọc PageResponse<ChapterResponse>
     */
    public PageResponse<ChapterResponse> filter(ChapterFilterRequest request) {
        Pageable pageable = PageUtils.from(request);
        var pageData = chapterRepository.findAll(ChapterSpecification.filter(request), pageable);

        return PageUtils.toPageResponse(
                pageData,
                chapterMapper::toChapterResponse,
                request.getPage()
        );
    }

    public String startReadChapter(StartReadChapterRequest request){
        request.setUserId(getUserIdFromContext());
        //ấy bookshelf hiện tại của user
        BookShelf bookShelf = bookShelfService.getCurrentActiveBookShelfOfUser(request.getUserId());
        //Nếu không có bookshelf thì ném lỗi
        if (bookShelf == null) {
            throw new AppException(ErrorCode.BOOKSHELF_NOT_FOUND);
        }
        //Nếu chưa có trong bookshelf thì tao moi
        BookShelfItem bookShelfItem = bookShelfItemService.getBookShelfItemOfUser(
                BookShelfItemGetRequest.builder()
                        .bookShelfId(bookShelf.getId())
                        .novelId(request.getNovelId())
                        .userId(request.getUserId())
                        .build()
        );
        BookShelfItemResponse bookShelfItemResponse = null;
        log.info("Bookshelf {}", bookShelfItem);
        if (bookShelfItem == null) {
            bookShelfItemResponse = bookShelfItemService.addItemToBookShelf(
                    bookShelf.getId(),
                    BookShelfItemCreateRequest.builder()
                            .bookShelfId(bookShelf.getId())
                            .currentChapterIdx(request.getCurrentChapterIdx())
                            .novelId(request.getNovelId())
                            .build()
            );
            log.info("BookShelfItemResponse create: {} - {}", bookShelfItemResponse.getId(), bookShelfItemResponse.getCurrentChapterIdx());
        }

        //Neu da co thì update lại chương mới
        else {
            bookShelfItemResponse = bookShelfItemService.updateBookShelfItem(
                    bookShelfItem.getId(),
                    BookShelfItemUpdateRequest.builder()
                            .bookShelfId(bookShelf.getId())
                            .currentChapterIdx(request.getCurrentChapterIdx())
                            .build()
            );
            log.info("BookShelfItemResponse update: {} - {}",bookShelfItemResponse.getId(), bookShelfItemResponse.getCurrentChapterIdx());
        }
        Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(request.getChapterId())
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        Novel novel = chapter.getNovel();
        String userId = getUserIdFromContext();
        var obj =  readingLogService.create(
                ReadingLogCreateRequest.builder()
                        .chapterId(chapter.getId())
                        .userId(userId)
                        .novelId(novel.getId())
                        .build());
        return obj.getId();
    }


    public Void navigateChapter(ChapterNavigationRequest request) {
        log.info("Navigate chapter request: {}", request);
        Chapter chapter = chapterRepository.findByIdAndIsDeletedIsFalse(request.getChapterId())
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        Novel novel = chapter.getNovel();
        int navigatedChapterIdx = 0;
        if (request.getIsNext()){
            navigatedChapterIdx = chapter.getChapterIdx() + 1;
        } else {
            navigatedChapterIdx = chapter.getChapterIdx() - 1;
        }
//        Chapter navigatedChapter = chapterRepository.findByNovelAndChapterIdxAndIsDeletedIsFalse(
//                novel,
//                navigatedChapterIdx
//        ).orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND)
//        );
        String userId = getUserIdFromContext();

//        checkUserCanReadChapterThrow(chapter, userId);
        boolean isFinished = true;
        if (request.getDuration() < 20 || request.getProgress() < 0.8f) {
            isFinished = false;
        }

        String logId = request.getLogId();
        if (logId != null){
            readingLogService.update(
                    logId,
                    ReadingLogUpdateRequest.builder()
                            .duration(request.getDuration())
                            .progress(request.getProgress())
                            .isFinished(isFinished)
                            .build()
            );
        }

        return null;
    }

    public Chapter findEntityById(String chapterId) {
        return chapterRepository.findByIdAndIsDeletedIsFalse(chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
    }

    public List<TopChapterResponse> findNTopLastedChaptersByNovelId(int n) {
        List<Chapter> chapters = chapterRepository.findTopNLatestPublishedChaptersPerNovel(n);
        List<TopChapterResponse> topChapters = new ArrayList<>();
        for (Chapter chapter : chapters) {
            TopChapterResponse topChapter = TopChapterResponse.builder()
                    .id(chapter.getId())
                    .name(chapter.getName())
                    .chapterIdx(chapter.getChapterIdx())
                    .createdAt(chapter.getCreatedAt())
                    .novel(novelMapper.toNovelResponse(chapter.getNovel()))
                    .build();
            topChapters.add(topChapter);
        }
        return topChapters;
    }

    public ChapterResponse getChapterByNovelSlugAndIdx(String slug, Integer idx) {
        Chapter chapter = chapterRepository.findByNovelSlugAndChapterIdxAndIsDeletedIsFalse(slug, idx)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
        return chapterMapper.toChapterResponse(chapter);
    }

    public List<Chapter> getChapterFromIndex(String novelId, int startIndex, int limit) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        return chapterRepository.findByNovelAndChapterIdxAndIsDeletedIsFalse(novel, startIndex)
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<ChapterResponse> getFromChaptersByNovelId(String novelId, int from, int size){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        Pageable pageable = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var pageData = chapterRepository.findByNovel(novel, pageable);

        return pageData.getContent()
                .stream()
                .map(chapterMapper::toChapterResponse)
                .toList();
    }

//    public List<Chapter> getChapter
}
