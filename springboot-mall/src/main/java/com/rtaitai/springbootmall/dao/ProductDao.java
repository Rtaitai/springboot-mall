package com.rtaitai.springbootmall.dao;

import com.rtaitai.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
