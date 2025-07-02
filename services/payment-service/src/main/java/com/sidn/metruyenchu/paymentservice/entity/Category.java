package com.sidn.metruyenchu.paymentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "categories")
    Set<Product> products;
}