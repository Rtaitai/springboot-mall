package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.dto.ProductRequest;
import com.rtaitai.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer  productId,ProductRequest productRequest);
}
