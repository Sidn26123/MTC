package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.chapter.*;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterResponse;
import com.sidn.metruyenchu.novelservice.dto.response.ChapterStatusResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterContentResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterListResponse;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterPublishCheckResponse;
import com.sidn.metruyenchu.novelservice.entity.Chapter;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatus;
import com.sidn.metruyenchu.novelservice.entity.ChapterStatusDetail;
import com.sidn.metruyenchu.novelservice.entity.Novel;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.ChapterMapper;
import com.sidn.metruyenchu.novelservice.repository.ChapterRepository;
import com.sidn.metruyenchu.novelservice.repository.ChapterStatusRepository;
import com.sidn.metruyenchu.novelservice.repository.NovelRepository;
import com.sidn.metruyenchu.novelservice.repository.httpclient.FileClient;
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

import java.util.ArrayList;
import java.util.List;


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
    ChapterStatusRepository chapterStatusRepository;

    FileClient fileClient;

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
        chapterMapper.update(chapter, request);
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



}
