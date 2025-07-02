package com.sidn.metruyenchu.paymentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Min(value = 1, message = "Price must be greater than or equal to 1")
    @Column(name = "price")
    double price;
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    @Column(name = "stock")
    int stock;
    @NotEmpty(message = "Category is mandatory")
    @ManyToMany
    Set<Category> categories;
}