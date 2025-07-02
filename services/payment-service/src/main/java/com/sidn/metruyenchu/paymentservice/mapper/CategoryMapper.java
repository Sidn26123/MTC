package com.sidn.metruyenchu.paymentservice.mapper;

import com.sidn.metruyenchu.paymentservice.dto.request.category.CategoryCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.category.CategoryResponse;
import com.sidn.metruyenchu.paymentservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    Category toCategory(CategoryCreateRequest categoryResponse);
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponses(List<Category> categories);
}
