package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.BaseFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.PageResponse;
import com.sidn.metruyenchu.novelservice.dto.request.feignclient.feedbackService.DeleteRatingRequest;
import com.sidn.metruyenchu.novelservice.dto.request.feignclient.feedbackService.RatingRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelFilterRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.*;
import com.sidn.metruyenchu.novelservice.dto.response.chapter.ChapterPublishCheckResponse;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelCanPublishResponse;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelResponse;
import com.sidn.metruyenchu.novelservice.dto.response.novel.NovelStatusResponse;
import com.sidn.metruyenchu.novelservice.entity.*;
import com.sidn.metruyenchu.novelservice.enums.NovelState;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.*;
import com.sidn.metruyenchu.novelservice.repository.*;
import com.sidn.metruyenchu.novelservice.spectification.NovelSpecification;
import com.sidn.metruyenchu.novelservice.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.sidn.metruyenchu.novelservice.utils.TokenUtils.getTokenFromContext;
import static com.sidn.metruyenchu.novelservice.utils.TokenUtils.getUserIdFromToken;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NovelService {
    NovelRepository novelRepository;
    NovelStatusRepository novelStatusRepository;
    NovelStatusDetailRepository novelStatusDetailRepository;
    MainCharacterTraitRepository mainCharacterTraitRepository;
    GenreRepository genreRepository;
    NovelMapper novelMapper;
    NovelAuthorMapper novelAuthorMapper;
    NovelStatusMapper novelStatusMapper;
    SectRepository sectRepository;
    WorldSceneRepository worldSceneRepository;
    NovelAuthorRepository novelAuthorRepository;
    ChapterService chapterService;

    SectMapper sectMapper;
    WorldSceneMapper worldSceneMapper;
    GenreMapper genreMapper;
    MainCharacterTraitMapper mainCharacterTraitMapper;
//    NovelAuthorRepository novelAuthorRepository;



    public NovelResponse getNovel(String novelName) {
        return getDataMissOfNovel(novelMapper.toNovelResponse(novelRepository.findByName(novelName)));
    }

    public PageResponse<NovelResponse> getNovels(
            NovelFilterRequest request
    ) {
        Pageable pageable = PageUtils.from(request);

        Page<Novel> novels = novelRepository.findAll(NovelSpecification.filter(request), pageable);


        List<NovelResponse> novelResponses = new ArrayList<>();

        for (Novel novel : novels){
            novelResponses.add(getDataWithManyRelationOfNovel(novel));
        }
        return PageResponse.<NovelResponse>builder()
                .currentPage(request.getPage())
                .pageSize(request.getSize())
                .totalPages(novels.getTotalPages())
                .totalElements(novels.getTotalElements())
//                .data(novels.stream().map(this::fetchDataMissOfNovel).toList())
                .data(novelResponses)
                .build();
    }

    public NovelResponse getNovelBySlug(String novelSlug) {
        Novel novel = novelRepository.findBySlug(novelSlug)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        NovelResponse response = novelMapper.toNovelResponse(novel);
        return getDataMissOfNovel(response);
    }


    @Transactional
    public NovelResponse createNovel(NovelCreationRequest request){
        Novel novel = novelMapper.toNovel(request);

        //get publisher
        String userId = getUserIdFromToken(getTokenFromContext());

        novel.setCurrentPublisher(userId);
        novel.setNovelCoverImage(getDefaultCoverImageUrl());

        if (request.getAuthorId() != null){
            NovelAuthor novelAuthor = novelAuthorRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
//            response.setAuthor(novelAuthorMapper.toNovelAuthorResponse(novelAuthor));
//            log.info("Author: {}", novelAuthor.getName());
//            log.info(response.getAuthor().getName());
            novel.setAuthor(novelAuthor);


        }

        try{
            novel = novelRepository.save(novel);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }



        NovelResponse response = novelMapper.toNovelResponse(novel);



        //Nếu có image cover
//        if (request.getNovelCoverImage() != null){
//
//        }
        response = fillData(novel, request, response);

        log.info(response.getAuthor().getName());
        return response;

    }
    public String getDefaultCoverImageUrl(){
        return "http://localhost:8889/api/v1/file/files/media/download/7e1a8df0-da25-45b8-b306-9cf34ca5a761.jpg";
    }

    public NovelCanPublishResponse checkNovelCanPublish(String novelId){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        int totalChapter = novel.getTotalChapters();

        NovelCanPublishResponse response = NovelCanPublishResponse.builder()
                .canPublish(true)
                .build();
        if (totalChapter < 1){
            response.setMessage(ErrorCode.NOVEL_NOT_HAVE_ENOUGH_CHAPTER.getMessage());
            response.setCanPublish(false);
            return response;
        }

        if (novel.getNovelState() == NovelState.PENDING){
            response.setMessage(ErrorCode.NOVEL_IS_PENDING.getMessage());
            response.setCanPublish(false);
            return response;
        }else if (novel.getNovelState() == NovelState.PUBLISHED) {
            response.setMessage(ErrorCode.NOVEL_IS_PUBLISHED.getMessage());
            response.setCanPublish(false);
            return response;
        }else if (novel.getNovelState() != NovelState.CREATED){
            response.setMessage("Novel is not in CREATED state");
            response.setCanPublish(false);
            return response;
        }

        List<ChapterPublishCheckResponse> chapterResponses = chapterService.checkChaptersSuitForPublish(novel.getChapters());
        if (chapterResponses.size() > 0){
            response.setCanPublish(false);
            response.setErrors(chapterResponses);
        } else {
            response.setCanPublish(true);
        }

        return response;
    }

//    public NovelStatus getDefaultNovelDetailStatus(){
//        return novelStatusDetailRepository.find
//    }

    public NovelResponse requestPublishNovel(String novelId){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        if (novel.getNovelState() != NovelState.CREATED){
            throw new AppException(ErrorCode.NOVEL_NOT_IN_CREATED_STATE);
        }

        novel.setNovelState(NovelState.PENDING);

        try {
            novel = novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return fetchDataMissOfNovel(novelMapper.toNovelResponse(novel));
    }

//    private void createNovelManyRelation(Novel novel, )
    private NovelResponse fillData(Novel novel, NovelCreationRequest request, NovelResponse response) {
        List<NovelStatus> novelStatuses = new ArrayList<>();
        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);

        //create novelstatusdetail
        List<NovelStatusDetail> novelStatusDetails = new ArrayList<>();
        for (NovelStatus novelStatus : novelStatuses) {
            NovelStatusDetail novelStatusDetail = NovelStatusDetail.builder()
                    .novel(novel)
                    .novelStatus(novelStatus)
                    .build();
            novelStatusDetails.add(novelStatusDetail);
        }
        List<NovelStatusResponse> novelStatusesResponse = novelStatuses.stream()
                .map(novelStatusMapper::toNovelStatusResponse)
                .toList();

        novel.setStatus(novelStatusDetails);

        List<Genre> genres = new ArrayList<>();
        genreRepository.findByIdIn(request.getGenreIds()).ifPresent(genres::add);

        List<NovelGenre> novelGenres = new ArrayList<>();
        for (Genre genre : genres) {
            NovelGenre genreDetail = NovelGenre.builder()
                    .novel(novel)
                    .genre(genre)
                    .build();
            novelGenres.add(genreDetail);
        }
        List<GenreResponse> genreResponses = genres.stream()
                .map(genreMapper::toGenreResponse)
                .toList();

        novel.setNovelGenres(novelGenres);


        List<MainCharacterTrait> mainCharacterTraits = new ArrayList<>();
        mainCharacterTraitRepository.findByIdIn(request.getMainCharacterTraitIds()).ifPresent(mainCharacterTraits::add);

        List<NovelMainCharacterTrait> novelMainCharTraits = new ArrayList<>();
        for (MainCharacterTrait mainCharacterTrait : mainCharacterTraits) {
            NovelMainCharacterTrait mainCharacterTraitDetail = NovelMainCharacterTrait.builder()
                    .novel(novel)
                    .mainCharacterTrait(mainCharacterTrait)
                    .build();
            novelMainCharTraits.add(mainCharacterTraitDetail);
        }
        List<MainCharacterTraitResponse> mainCharacterTraitResponses = mainCharacterTraits.stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .toList();

        novel.setNovelMainCharacterTraits(novelMainCharTraits);

        //get worldscene from worldsceneIds in request
        List<WorldScene> worldScenes = new ArrayList<>();
        worldSceneRepository.findByIdIn(request.getWorldSceneIds()).ifPresent(worldScenes::add);

        //create novel-worldscene relation
        List<NovelWorldScene> novelWorldScenes = new ArrayList<>();
        for (WorldScene worldScene : worldScenes){
            NovelWorldScene novelWorldScene = NovelWorldScene.builder()
                    .worldScene(worldScene)
                    .novel(novel)
                    .build();
        }
        List<WorldSceneResponse> worldSceneResponses = worldScenes.stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .toList();

        novel.setNovelWorldScenes(novelWorldScenes);

        List<Sect> sects = new ArrayList<>();
        sects = sectRepository.findByIdIn(request.getSectIds());

        List<NovelSect> novelSects = new ArrayList<>();
        for (Sect sect : sects) {
            NovelSect sectDetail = NovelSect.builder()
                    .novel(novel)
                    .sect(sect)
                    .build();
            novelSects.add(sectDetail);
        }
        List<SectResponse> sectResponses = sects.stream()
                .map(sectMapper::toSectResponse)
                .toList();

        novel.setNovelSects(novelSects);


        log.info("Novel: {}", novel.getName());
        log.info("Sect: {}", sectResponses.getFirst().getName());
        log.info("Novel: {}", novel.getNovelSects());
        log.info("Name: {}", worldScenes.getFirst().getName());
        log.info("Name: {}", mainCharacterTraits.getFirst().getName());


        try {
            novel = novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

//        NovelResponse response = novelMapper.toNovelResponse(novel);
        response.setStatus(novelStatusesResponse);
        response.setGenres(genreResponses);
        response.setMainCharacterTraits(mainCharacterTraitResponses);
        response.setSects(sectResponses);
        response.setWorldScenes(worldSceneResponses);
        return response;
    }

    private NovelResponse fillData(Novel novel, NovelUpdateRequest request){
        List<NovelStatus> novelStatuses = new ArrayList<>();
        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);

        //create novelstatusdetail
        List<NovelStatusDetail> novelStatusDetails = new ArrayList<>();
        for (NovelStatus novelStatus : novelStatuses) {
            NovelStatusDetail novelStatusDetail = NovelStatusDetail.builder()
                    .novel(novel)
                    .novelStatus(novelStatus)
                    .build();
            novelStatusDetails.add(novelStatusDetail);
        }
        List<NovelStatusResponse> novelStatusesResponse = novelStatuses.stream()
                .map(novelStatusMapper::toNovelStatusResponse)
                .toList();

        novel.setStatus(novelStatusDetails);

        List<Genre> genres = new ArrayList<>();
        genreRepository.findByIdIn(request.getGenreIds()).ifPresent(genres::add);

        List<NovelGenre> novelGenres = new ArrayList<>();
        for (Genre genre : genres) {
            NovelGenre genreDetail = NovelGenre.builder()
                    .novel(novel)
                    .genre(genre)
                    .build();
            novelGenres.add(genreDetail);
        }
        List<GenreResponse> genreResponses = genres.stream()
                .map(genreMapper::toGenreResponse)
                .toList();

        novel.setNovelGenres(novelGenres);


        List<MainCharacterTrait> mainCharacterTraits = new ArrayList<>();
        mainCharacterTraitRepository.findByIdIn(request.getMainCharacterTraitIds()).ifPresent(mainCharacterTraits::add);

        List<NovelMainCharacterTrait> novelMainCharTraits = new ArrayList<>();
        for (MainCharacterTrait mainCharacterTrait : mainCharacterTraits) {
            NovelMainCharacterTrait mainCharacterTraitDetail = NovelMainCharacterTrait.builder()
                    .novel(novel)
                    .mainCharacterTrait(mainCharacterTrait)
                    .build();
            novelMainCharTraits.add(mainCharacterTraitDetail);
        }
        List<MainCharacterTraitResponse> mainCharacterTraitResponses = mainCharacterTraits.stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .toList();

        novel.setNovelMainCharacterTraits(novelMainCharTraits);

        //get worldscene from worldsceneIds in request
        List<WorldScene> worldScenes = new ArrayList<>();
        worldSceneRepository.findByIdIn(request.getWorldSceneIds()).ifPresent(worldScenes::add);

        //create novel-worldscene relation
        List<NovelWorldScene> novelWorldScenes = new ArrayList<>();
        for (WorldScene worldScene : worldScenes){
            NovelWorldScene novelWorldScene = NovelWorldScene.builder()
                    .worldScene(worldScene)
                    .novel(novel)
                    .build();
        }
        List<WorldSceneResponse> worldSceneResponses = worldScenes.stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .toList();

        novel.setNovelWorldScenes(novelWorldScenes);

        List<Sect> sects = new ArrayList<>();
        sects =sectRepository.findByIdIn(request.getSectIds());

        List<NovelSect> novelSects = new ArrayList<>();
        for (Sect sect : sects) {
            NovelSect sectDetail = NovelSect.builder()
                    .novel(novel)
                    .sect(sect)
                    .build();
            novelSects.add(sectDetail);
        }
        List<SectResponse> sectResponses = sects.stream()
                .map(sectMapper::toSectResponse)
                .toList();

        novel.setNovelSects(novelSects);

        try {
            novel = novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        NovelResponse response = novelMapper.toNovelResponse(novel);
        response.setStatus(novelStatusesResponse);
        response.setGenres(genreResponses);
        response.setMainCharacterTraits(mainCharacterTraitResponses);
        response.setSects(sectResponses);
        response.setWorldScenes(worldSceneResponses);
        return response;
    }

    private <T, R, E> List<E> processEntities(
            List<String> ids,
            Function<List<String>, Optional<List<T>>> findByIdFunction,
            Function<T, R> toResponseMapper,
            BiFunction<Novel, T, E> entityBuilder,
            Novel novel
    ) {
        List<T> entities = new ArrayList<>();
        findByIdFunction.apply(ids).ifPresent(entities::addAll);

        List<E> novelEntities = entities.stream()
                .map(entity -> entityBuilder.apply(novel, entity))
                .toList();


        return novelEntities;
    }


    public PageResponse<NovelResponse> getAllNovels(BaseFilterRequest request) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
//        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Pageable pageable = PageUtils.from(request);

        var pageData = novelRepository.findAll(pageable);

        List<Novel> novels = pageData.getContent();

        List<NovelResponse> novelResponses = new ArrayList<>();

        for (Novel novel : novels){
            novelResponses.add(getDataWithManyRelationOfNovel(novel));
        }

        return PageResponse.<NovelResponse>builder()
                .currentPage(request.getPage())
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
//                .data(pageData.getContent().stream().map(this::fetchDataMissOfNovel).toList())
                .data(novelResponses)
                .build();
    }

    public NovelResponse getNovelById(String novelId) {
        return fetchDataMissOfNovel(novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND)));
    }

    public NovelResponse updateNovel(String novelId, NovelUpdateRequest request){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
//
////        Optional<NovelAuthor> novelAuthor = novelAuthorRepository.findById(String.valueOf(request.getAuthorId()));
////        if (novelAuthor.isEmpty()){
////            throw new AppException(ErrorCode.UNKNOWN_ERROR);
////        }
//        NovelAuthor novelAuthor = novelAuthorRepository.findById(request.getAuthorId())
//                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
//        novelMapper.updateNovel(novel, request);
//
//        log.info(novelAuthor.getName());
//        novel.setAuthor(novelAuthor);
//
//        try {
//            novel = novelRepository.save(novel);
//        } catch (Exception exception){
//            throw new AppException(ErrorCode.UNKNOWN_ERROR);
//        }
//
//        return fillData(novel, request);
        novelMapper.updateNovel(novel, request);
//        return novelMapper.toNovelResponse(novel);

        return fillData(novel, request);
    }

    public void deleteNovel(String novelId) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));
        novel.setIsDeleted(true);
        try {
            novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

//    public List<NovelResponse> searchNovel(String keyword) {
//        return novelRepository.findByNameContaining(keyword).stream()
//                .map(this::fetchDataMissOfNovel)
//                .toList();
//    }

    public List<NovelResponse> getNovelByAuthorId(String authorId) {
        return novelRepository.findByAuthorId(authorId).stream()
                .map(this::fetchDataMissOfNovel)
                .toList();
    }

    public NovelResponse fetchDataMissOfNovel(Novel novel){
        NovelResponse novelResponse = novelMapper.toNovelResponse(novel);

        return fetchDataMissOfNovel(novelResponse);
    }

    public NovelResponse fetchDataMissOfNovel(NovelResponse novelResponse){
        Novel novel = novelRepository.findById(novelResponse.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        List<NovelStatus> novelStatuses = novelStatusRepository.findByIdIn(
                novel.getStatus().stream()
                        .map(NovelStatusDetail::getNovelStatus)
                        .map(NovelStatus::getId)
                        .toList()
        ).stream().toList();
        List<NovelStatusResponse> novelStatusesResponse = novelStatuses.stream()
                .map(novelStatusMapper::toNovelStatusResponse)
                .toList();
        novelResponse.setStatus(novelStatusesResponse);

        return novelResponse;
    }

    private NovelResponse getDataMissOfNovel(NovelResponse novelResponse){
        Novel novel = novelRepository.findById(novelResponse.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        List<Genre> genres = genreRepository.findByIdIn(
                novel.getNovelGenres().stream()
                        .map(NovelGenre::getGenre)
                        .map(Genre::getId)
                        .toList()
        ).stream().toList();
        List<GenreResponse> genreResponses = genres.stream()
                .map(genreMapper::toGenreResponse)
                .toList();

        log.info(genreResponses.toString());
        novelResponse.setGenres(genreResponses);

        List<MainCharacterTrait> mainCharacterTraits = mainCharacterTraitRepository.findByIdIn(
                novel.getNovelMainCharacterTraits().stream()
                        .map(NovelMainCharacterTrait::getMainCharacterTrait)
                        .map(MainCharacterTrait::getId)
                        .toList()
        ).stream().toList();
        List<MainCharacterTraitResponse> mainCharacterTraitResponses = mainCharacterTraits.stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .toList();
        novelResponse.setMainCharacterTraits(mainCharacterTraitResponses);

        List<WorldScene> worldScenes = worldSceneRepository.findByIdIn(
                novel.getNovelWorldScenes().stream()
                        .map(NovelWorldScene::getWorldScene)
                        .map(WorldScene::getId)
                        .toList()
        ).stream().toList();
        List<WorldSceneResponse> worldSceneResponses = worldScenes.stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .toList();
        novelResponse.setWorldScenes(worldSceneResponses);

        List<Sect> sects = sectRepository.findByIdIn(
                novel.getNovelSects().stream()
                        .map(NovelSect::getSect)
                        .map(Sect::getId)
                        .toList()
        ).stream().toList();
        List<SectResponse> sectResponses = sects.stream()
                .map(sectMapper::toSectResponse)
                .toList();
        novelResponse.setSects(sectResponses);

        NovelAuthor novelAuthor = novel.getAuthor();
        if (novelAuthor != null){
            novelResponse.setAuthor(novelAuthorMapper.toNovelAuthorResponse(novelAuthor));
        }

        return novelResponse;
    }

    private NovelResponse getDataWithManyRelationOfNovel(Novel novel){
        NovelResponse novelResponse = novelMapper.toNovelResponse(novel);

        List<Genre> genres = genreRepository.findByIdIn(
                novel.getNovelGenres().stream()
                        .map(NovelGenre::getGenre)
                        .map(Genre::getId)
                        .toList()
        ).stream().toList();
        List<GenreResponse> genreResponses = genres.stream()
                .map(genreMapper::toGenreResponse)
                .toList();
        novelResponse.setGenres(genreResponses);

        List<MainCharacterTrait> mainCharacterTraits = mainCharacterTraitRepository.findByIdIn(
                novel.getNovelMainCharacterTraits().stream()
                        .map(NovelMainCharacterTrait::getMainCharacterTrait)
                        .map(MainCharacterTrait::getId)
                        .toList()
        ).stream().toList();
        List<MainCharacterTraitResponse> mainCharacterTraitResponses = mainCharacterTraits.stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .toList();
        novelResponse.setMainCharacterTraits(mainCharacterTraitResponses);

        List<WorldScene> worldScenes = worldSceneRepository.findByIdIn(
                novel.getNovelWorldScenes().stream()
                        .map(NovelWorldScene::getWorldScene)
                        .map(WorldScene::getId)
                        .toList()
        ).stream().toList();
        List<WorldSceneResponse> worldSceneResponses = worldScenes.stream()
                .map(worldSceneMapper::toWorldSceneResponse)
                .toList();
        novelResponse.setWorldScenes(worldSceneResponses);

        List<Sect> sects = sectRepository.findByIdIn(
                novel.getNovelSects().stream()
                        .map(NovelSect::getSect)
                        .map(Sect::getId)
                        .toList()
        ).stream().toList();
        List<SectResponse> sectResponses = sects.stream()
                .map(sectMapper::toSectResponse)
                .toList();
        novelResponse.setSects(sectResponses);

        NovelAuthor novelAuthor = novel.getAuthor();
        if (novelAuthor != null){
            novelResponse.setAuthor(novelAuthorMapper.toNovelAuthorResponse(novelAuthor));
        }

        return novelResponse;
    }

    public int getAllNovel(boolean containDeleted){
        return novelRepository.getAllNovel(containDeleted);
    }

    public PageResponse<NovelResponse> getNovelWithFilter(NovelFilterRequest request){
//        Sort sort = Sort.by(Sort.Direction.DESC, "created_at");
//        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
        Pageable pageable = PageUtils.from(request);

//        Page<Novel> novels = novelRepository.findAll(NovelSpecification.filter(request), pageable);
        Page<Novel> novels = novelRepository.findNovelWithFilter(request, pageable);
        List<NovelResponse> novelResponses = new ArrayList<>();

        for (Novel novel : novels){
            novelResponses.add(getDataWithManyRelationOfNovel(novel));
        }
//        return PageResponse.<NovelResponse>builder()
//                .currentPage(request.getPage())
//                .pageSize(request.getSize())
//                .totalPages(novels.getTotalPages())
//                .totalElements(novels.getTotalElements())
////                .data(novels.stream().map(this::fetchDataMissOfNovel).toList())
//                .data(novelResponses)
//                .build();
        return PageUtils.toPageResponse(novels,
                novelMapper::toNovelResponse,
                request.getPage()
        );
    }

//    public PageResponse<NovelBasicInfoResponse> getNovelOfAuthor(NovelOfAuthorFilterRequest request){
//        Pageable pageable = PageUtils.from(request);
//
//
//    }

    /**
     * Rating novel
     * @param novelId
     * @param request
     * @return
     */
    public NovelResponse ratingNovel(String novelId, RatingRequest request){
        //Xử lý theo thông số
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        //Neu chua có rating
        if (request.getIsNew()){
            novel.setTotalRates(novel.getTotalRates() + 1);
            novel.setAvgRate((novel.getAvgRate() * (novel.getTotalRates() - 1) + request.getRate()) / novel.getTotalRates());
        } else {
            // Nếu đã rating và cập nạạt lại rating tổng
            novel.setAvgRate((novel.getAvgRate() * (novel.getTotalRates()) - request.getOldRate() + request.getRate()) / novel.getTotalRates());
        }

        //Update here
        return updateNovel(novelId, NovelUpdateRequest.builder()
                .avgRate(novel.getAvgRate())
                .totalRates(novel.getTotalRates())
                .build());
        //Cập nhật lại theo NovelUpdateRequest
    }

    //user remove rating
    public NovelResponse removeRating(String novelId, DeleteRatingRequest request){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        //Nếu đã rating và cập nạạt lại rating tổng
        novel.setAvgRate((novel.getAvgRate() * (novel.getTotalRates()) - request.getRate()) / (novel.getTotalRates() - 1));
        novel.setTotalRates(novel.getTotalRates() - 1);

        return updateNovel(novelId, NovelUpdateRequest.builder()
                .avgRate(novel.getAvgRate())
                .totalRates(novel.getTotalRates())
                .build());
    }


}
