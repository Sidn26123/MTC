package com.sidn.metruyenchu.paymentservice.mapper;

import com.sidn.metruyenchu.paymentservice.dto.request.product.ProductCreateRequest;
import com.sidn.metruyenchu.paymentservice.dto.response.product.ProductResponse;
import com.sidn.metruyenchu.paymentservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
    Product toProduct(ProductCreateRequest request);
    List<ProductResponse> toProductResponses(List<Product> products);

}
