package com.rtaitai.springbootmall.entity;

import com.rtaitai.springbootmall.constant.ProductCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private String imageUrl;

    private Integer price;

    private Integer stock;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
