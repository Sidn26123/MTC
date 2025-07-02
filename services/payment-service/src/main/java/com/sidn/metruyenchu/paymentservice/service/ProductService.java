package com.sidn.metruyenchu.paymentservice.service;

import com.sidn.metruyenchu.paymentservice.dto.request.product.ProductCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.request.product.ProductUpdateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.product.ProductResponse;
import com.sidn.metruyenchu.paymentservice.entity.Category;
import com.sidn.metruyenchu.paymentservice.entity.Product;
import com.sidn.metruyenchu.paymentservice.exception.AppException;
import com.sidn.metruyenchu.paymentservice.exception.ErrorCode;
import com.sidn.metruyenchu.paymentservice.mapper.ProductMapper;
import com.sidn.metruyenchu.paymentservice.repository.ICategoryRepository;
import com.sidn.metruyenchu.paymentservice.repository.IProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {

    IProductRepository productRepository;
    ICategoryRepository categoryRepository;
    ProductMapper productMapper;

    public ProductResponse createProduct(ProductCreateRequest request) {
        log.info("ProductService.createProduct");

        Product product = productMapper.toProduct(request);
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));

        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        product.setCategories(categories);

        return productMapper.toProductResponse(
                productRepository.save(product)
        );
    }

    public ProductResponse getProduct(String id) {
        log.info("ProductService.getProduct");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(ProductUpdateRequest request) {
        log.info("ProductService.updateProduct");

        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        product.setCategories(categories);

        return productMapper.toProductResponse(
                productRepository.save(product)
        );
    }

    public void deleteProduct(String id) {
        log.info("ProductService.deleteProduct");
        productRepository.deleteById(id);
    }

    public List<ProductResponse> getAllProducts() {
        log.info("ProductService.getAllProducts");
        return productMapper.toProductResponses(
                productRepository.findAll()
        );
    }
}
