package com.sidn.metruyenchu.paymentservice.dto.response.product;

import com.sidn.metruyenchu.paymentservice.dto.response.category.CategoryResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String name;
    double price;
    int stock;
    String description;
    List<CategoryResponse> categories;
}
