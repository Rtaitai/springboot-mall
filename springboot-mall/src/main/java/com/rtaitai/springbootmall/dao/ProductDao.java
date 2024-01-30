package com.rtaitai.springbootmall.dao;

import com.rtaitai.springbootmall.dto.ProductRequest;
import com.rtaitai.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
