package com.rtaitai.springbootmall.dao;

import com.rtaitai.springbootmall.dto.ProductRequest;
import com.rtaitai.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
