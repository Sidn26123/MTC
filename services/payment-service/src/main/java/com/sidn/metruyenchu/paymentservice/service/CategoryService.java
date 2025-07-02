package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.category.CategoryCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.category.CategoryUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.category.CategoryResponse;
import com.sidn.metruyenchu.paymentservice.entity.Category;
import com.sidn.metruyenchu.paymentservice.exception.AppException;
import com.sidn.metruyenchu.paymentservice.exception.ErrorCode;
import com.sidn.metruyenchu.paymentservice.mapper.CategoryMapper;
import com.sidn.metruyenchu.paymentservice.repository.ICategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    ICategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse addCategory(CategoryCreateRequest category) {
        log.info("CategoryService.addCategory");
        return categoryMapper.toCategoryResponse(
                categoryRepository.save(
                        categoryMapper.toCategory(category)
                )
        );
    }

    public CategoryResponse getCategory(String id) {
        log.info("CategoryService.getCategory");
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Category not found"));

        return categoryMapper.toCategoryResponse(category);
    }

    public Void deleteCategory(String id) {
        log.info("CategoryService.deleteCategory");
        categoryRepository.deleteById(id);

        return null;
    }
    public CategoryResponse updateCategory(CategoryUpdateRequest request) {
        log.info("CategoryService.updateCategory");
        Category categoryToUpdate = categoryRepository.findById(request.getId()).orElseThrow(() ->
                new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryToUpdate.setName(request.getName());
        categoryToUpdate.setDescription(request.getDescription());
//        categoryToUpdate.set(LocalDateTime.now());
        return categoryMapper.toCategoryResponse(
                categoryRepository.save(categoryToUpdate)
        );
    }

    public List<CategoryResponse> getCategories() {
        log.info("CategoryService.getCategories");
        return categoryMapper.toCategoryResponses(
                categoryRepository.findAll()
        );
    }
}
