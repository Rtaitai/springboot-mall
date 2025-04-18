package com.rtaitai.springbootmall.mapper;

import com.rtaitai.springbootmall.dto.ProductQueryParams;
import com.rtaitai.springbootmall.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> getProducts(ProductQueryParams params);

    Integer countProduct(ProductQueryParams params);

    void createProduct(Product product);

}
