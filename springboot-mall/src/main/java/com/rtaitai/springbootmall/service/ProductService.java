package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.constant.ProductCategory;
import com.rtaitai.springbootmall.dto.ProductQueryParams;
import com.rtaitai.springbootmall.dto.ProductRequest;
import com.rtaitai.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer  productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
