package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.category.CategoryCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.category.CategoryDeleteRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.category.CategoryUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.category.CategoryResponse;
import com.sidn.metruyenchu.paymentservice.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ApiResponse<List<CategoryResponse>> getAll() {
//        return new ApiResponse<>(HttpStatus.OK, "Fetch all category completed",categoryService.getCategories());
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getCategories())
                .build();
        
    }
    @PostMapping("/add")
    public ApiResponse<CategoryResponse> add(@RequestBody CategoryCreateRequest createRequest) {
//        return new ApiResponse<>(HttpStatus.CREATED, "Add category completed", categoryService.addCategory(createRequest));
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.addCategory(createRequest))
                .build();

    }
    @PutMapping("/update")
    public ApiResponse<CategoryResponse> update(@RequestBody CategoryUpdateRequest updateRequest) {
//        return new ApiResponse<>(HttpStatus.ACCEPTED, "Update category completed", categoryService.updateCategory(updateRequest));

        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(updateRequest))
                .build();

    }
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {

        CategoryDeleteRequest deleteRequest = new CategoryDeleteRequest();

        deleteRequest.setId(id);

        return ApiResponse.<Void>builder()
                .result(categoryService.deleteCategory(id))
                .build();

    }
}
