package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.response.category.CategoryResponse;
import com.sidn.metruyenchu.novelservice.entity.Genre;
import com.sidn.metruyenchu.novelservice.entity.MainCharacterTrait;
import com.sidn.metruyenchu.novelservice.entity.Sect;
import com.sidn.metruyenchu.novelservice.entity.WorldScene;
import com.sidn.metruyenchu.novelservice.enums.*;
import com.sidn.metruyenchu.novelservice.repository.GenreRepository;
import com.sidn.metruyenchu.novelservice.repository.MainCharacterTraitRepository;
import com.sidn.metruyenchu.novelservice.repository.SectRepository;
import com.sidn.metruyenchu.novelservice.repository.WorldSceneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {

    GenreRepository genreRepository;
    SectRepository sectRepository;
    MainCharacterTraitRepository mainCharacterTraitRepository;
    WorldSceneRepository worldSceneRepository;

    /**
     * Lấy danh sách trạng thái tiến độ truyện (đang ra, hoàn thành).
     *
     * @return danh sách CategoryResponse chứa mã và nhãn trạng thái.
     */
    public List<CategoryResponse> getNovelProgressStatus() {
        return Arrays.stream(ProgressStatus.values())
                .map(status -> new CategoryResponse(status.name(), status.getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách thuộc tính truyện từ enum NovelAttribute.
     *
     * @return danh sách CategoryResponse.
     */
    public List<CategoryResponse> getNovelAttributes() {
        return Arrays.stream(NovelAttribute.values())
                .map(item -> new CategoryResponse(item.name(), item.getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách trạng thái truyện (khởi tạo, chờ duyệt, đã duyệt, ...).
     *
     * @return danh sách CategoryResponse.
     */
    public List<CategoryResponse> getNovelState() {
        return Arrays.stream(NovelState.values())
                .map(item -> new CategoryResponse(item.name(), item.getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách chế độ hiển thị truyện (public, private, ...).
     *
     * @return danh sách CategoryResponse.
     */
    public List<CategoryResponse> getNovelVisibility() {
        return Arrays.stream(NovelVisibility.values())
                .map(item -> new CategoryResponse(item.name(), item.getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách loại truyện từ enum NovelType.
     *
     * @return danh sách CategoryResponse.
     */
    public List<CategoryResponse> getNovelType() {
        return Arrays.stream(NovelType.values())
                .map(item -> new CategoryResponse(item.name(), item.getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách thể loại truyện từ database.
     *
     * @return danh sách CategoryResponse với id và tên thể loại.
     */
    public List<CategoryResponse> getGenresList() {
        List<Genre> genres = genreRepository.findAllByIsActiveAndIsDeletedOrderByName(true, false);
        return genres.stream()
                .map(genre -> new CategoryResponse(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách đặc điểm nhân vật chính từ database.
     *
     * @return danh sách CategoryResponse với id và tên.
     */
    public List<CategoryResponse> getMainCharacterList() {
        List<MainCharacterTrait> items = mainCharacterTraitRepository.findAllByIsActiveAndIsDeletedOrderByName(true, false);
        return items.stream()
                .map(item -> new CategoryResponse(item.getId(), item.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách môn phái từ database.
     *
     * @return danh sách CategoryResponse với id và tên.
     */
    public List<CategoryResponse> getSectList() {
        List<Sect> items = sectRepository.findAllByIsActiveAndIsDeletedOrderByName(true, false);
        return items.stream()
                .map(item -> new CategoryResponse(item.getId(), item.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách bối cảnh thế giới truyện từ database.
     *
     * @return danh sách CategoryResponse với id và tên.
     */
    public List<CategoryResponse> getWorldSceneList() {
        List<WorldScene> items = worldSceneRepository.findAllByIsActiveAndIsDeletedOrderByName(true, false);
        return items.stream()
                .map(item -> new CategoryResponse(item.getId(), item.getName()))
                .collect(Collectors.toList());
    }
}

