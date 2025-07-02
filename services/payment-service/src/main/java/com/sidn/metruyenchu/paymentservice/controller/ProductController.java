package com.sidn.metruyenchu.paymentservice.controller;

import com.sidn.metruyenchu.paymentservice.dto.ApiResponse;
import com.sidn.metruyenchu.paymentservice.dto.request.product.ProductCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.product.ProductUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.product.ProductResponse;
import com.sidn.metruyenchu.paymentservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {

    ProductService productService;

    @GetMapping("/all")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        log.info("ProductController.getAllProducts");
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable String id) {
        log.info("ProductController.getProduct: {}", id);
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProduct(id))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<ProductResponse> addProduct(@Valid @RequestBody ProductCreateRequest request) {
        log.info("ProductController.addProduct");
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<ProductResponse> updateProduct(@RequestBody ProductUpdateRequest request) {
        log.info("ProductController.updateProduct");
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable String id) {
        log.info("ProductController.deleteProduct: {}", id);
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .build(); // result = null => đúng format chung
    }
}
