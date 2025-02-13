package com.sidn.metruyenchu.novelservice.service;

import com.nimbusds.jose.JOSEException;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.request.novel.NovelUpdateRequest;
import com.sidn.metruyenchu.novelservice.dto.response.*;
import com.sidn.metruyenchu.novelservice.entity.*;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.*;
import com.sidn.metruyenchu.novelservice.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    NovelStatusMapper novelStatusMapper;
    SectRepository sectRepository;

    SectMapper sectMapper;
    WorldSceneMapper worldSceneMapper;
    GenreMapper genreMapper;
    MainCharacterTraitMapper mainCharacterTraitMapper;
    public NovelResponse getNovel(String novelName) {
        return fetchDataMissOfNovel(novelMapper.toNovelResponse(novelRepository.findByName(novelName)));
    }

    public NovelResponse getNovelBySlug(String novelSlug) {

        return fetchDataMissOfNovel(novelMapper.toNovelResponse(novelRepository.findBySlug(novelSlug)));
    }

    public NovelResponse createNovel(NovelCreationRequest request){
        Novel novel = novelMapper.toNovel(request);

        //get publisher
        String userId = getUserIdFromToken(getTokenFromContext());

        novel.setCurrentPublisher(userId);

        try{
            novel = novelRepository.save(novel);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return fillData(novel, request);

    }

    private NovelResponse fillData(Novel novel, NovelCreationRequest request) {
        HashSet<NovelStatus> novelStatuses = new HashSet<>();
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
        mainCharacterTraitRepository.findByIdIn(request.getMainCharTraitIds()).ifPresent(mainCharacterTraits::add);

        List<NovelMainCharTrait> novelMainCharTraits = new ArrayList<>();
        for (MainCharacterTrait mainCharacterTrait : mainCharacterTraits) {
            NovelMainCharTrait mainCharacterTraitDetail = NovelMainCharTrait.builder()
                    .novel(novel)
                    .mainCharacterTrait(mainCharacterTrait)
                    .build();
            novelMainCharTraits.add(mainCharacterTraitDetail);
        }
        List<MainCharacterTraitResponse> mainCharacterTraitResponses = mainCharacterTraits.stream()
                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
                .toList();

        novel.setNovelMainCharTraits(novelMainCharTraits);

        List<Sect> sects = new ArrayList<>();
        sectRepository.findByIdIn(request.getSectIds()).ifPresent(sects::add);

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


//    public NovelResponse fillDataYetMapped(NovelCreationRequest request, NovelResponse response, Novel novel){
//        List<NovelStatus> novelStatuses = new ArrayList<>();
//        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);
//
//        //create novelstatusdetail
//        List<NovelStatusDetail> novelStatusDetails = new ArrayList<>();
//        for (NovelStatus novelStatus : novelStatuses) {
//            NovelStatusDetail novelStatusDetail = NovelStatusDetail.builder()
//                    .novel(novel)
//                    .novelStatus(novelStatus)
//                    .build();
//            novelStatusDetails.add(novelStatusDetail);
//        }
//        List<NovelStatusResponse> novelStatusesResponse = novelStatuses.stream()
//                .map(novelStatusMapper::toNovelStatusResponse)
//                .toList();
//
//        response.setStatus(novelStatusesResponse);
//
//        List<Genre> genres = new ArrayList<>();
//        genreRepository.findByIdIn(request.getGenreIds()).ifPresent(genres::add);
//
//        List<NovelGenre> novelGenres = new ArrayList<>();
//        for (Genre genre : genres) {
//            NovelGenre genreDetail = NovelGenre.builder()
//                    .novel(novel)
//                    .genre(genre)
//                    .build();
//            novelGenres.add(genreDetail);
//        }
//        List<GenreResponse> genreResponses = genres.stream()
//                .map(genreMapper::toGenreResponse)
//                .toList();
//
//        response.setGenres(genreResponses);
//
//
//        List<MainCharacterTrait> mainCharacterTraits = new ArrayList<>();
//        mainCharacterTraitRepository.findByIdIn(request.getMainCharTraitIds()).ifPresent(mainCharacterTraits::add);
//
//        List<NovelMainCharTrait> novelMainCharTraits = new ArrayList<>();
//        for (MainCharacterTrait mainCharacterTrait : mainCharacterTraits) {
//            NovelMainCharTrait mainCharacterTraitDetail = NovelMainCharTrait.builder()
//                    .novel(novel)
//                    .mainCharacterTrait(mainCharacterTrait)
//                    .build();
//            novelMainCharTraits.add(mainCharacterTraitDetail);
//        }
//        List<MainCharacterTraitResponse> mainCharacterTraitResponses = mainCharacterTraits.stream()
//                .map(mainCharacterTraitMapper::toMainCharacterTraitResponse)
//                .toList();
//
//        response.setMainCharacterTraits(mainCharacterTraitResponses);
//
//        List<Sect> sects = new ArrayList<>();
//        sectRepository.findByIdIn(request.getSectIds()).ifPresent(sects::add);
//
//        List<NovelSect> novelSects = new ArrayList<>();
//        for (Sect sect : sects) {
//            NovelSect sectDetail = NovelSect.builder()
//                    .novel(novel)
//                    .sect(sect)
//                    .build();
//            novelSects.add(sectDetail);
//        }
//        List<SectResponse> sectResponses = sects.stream()
//                .map(sectMapper::toSectResponse)
//                .toList();
//
//        response.setSects(sectResponses);
//
//        return response;
//    }

//    public NovelResponse createNovel(NovelCreationRequest request) {
//        Novel novel = novelMapper.toNovel(request);
//        HashSet<NovelStatus> novelStatuses = new HashSet<>();
//        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);
//        String userId = null;
//        try {
//            userId = getUserIdFromToken(getTokenFromContext());
//        } catch (ParseException | JOSEException e) {
//            e.printStackTrace();
//        }
//        novel.setCurrentPublisher(userId);
//        try{
//            novel = novelRepository.save(novel);
//        } catch (Exception exception) {
//            throw new AppException(ErrorCode.UNKNOWN_ERROR);
//        }
//
//        //create novelstatusdetail
//        List<NovelStatusDetail> novelStatusDetails = new ArrayList<>();
//        for (NovelStatus novelStatus : novelStatuses) {
//            NovelStatusDetail novelStatusDetail = NovelStatusDetail.builder()
//                    .novel(novel)
//                    .novelStatus(novelStatus)
//                    .build();
//            novelStatusDetails.add(novelStatusDetail);
//        }
//
//        novelStatusDetailRepository.saveAll(novelStatusDetails);
//
//        novel.setStatus(novelStatusDetails);
//
//
//
////        NovelResponse novelResponse = novelMapper.toNovelResponse(novel);
////        List<NovelStatusResponse> novelStatusesResponse = novel.getStatus().stream()
////                .map(novelStatusMapper::toNovelStatusResponse)
////                .toList();
////        novelResponse.setStatus(novelStatusesResponse);
//        return fetchDataMissOfNovel(novel);
//    }

    public List<NovelResponse> getAllNovel(){
//        try{
//            log.info(getUserIdFromToken(getTokenFromContext()));
//        } catch (ParseException | JOSEException e) {
//            e.printStackTrace();
//        }
        return novelRepository.findAll().stream()
                .map(this::fetchDataMissOfNovel)
                .toList();
    }

    public NovelResponse getNovelById(String novelId) {
        return fetchDataMissOfNovel(novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND)));
    }

    public NovelResponse updateNovel(String novelId, NovelUpdateRequest request){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOVEL_NOT_FOUND));

        novelMapper.updateNovel(novel, request);

        HashSet<NovelStatus> novelStatuses = new HashSet<>();
        novelStatusRepository.findByIdIn(request.getStatus()).ifPresent(novelStatuses::add);
//        novel.setStatus(novelStatuses);


        try {
            novel = novelRepository.save(novel);
        } catch (Exception exception){
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return fetchDataMissOfNovel(novel);


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

    public List<NovelResponse> searchNovel(String keyword) {
        return novelRepository.findByNameContaining(keyword).stream()
                .map(this::fetchDataMissOfNovel)
                .toList();
    }


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
}
