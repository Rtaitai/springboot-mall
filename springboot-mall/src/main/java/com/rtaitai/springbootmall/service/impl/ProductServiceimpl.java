package com.rtaitai.springbootmall.service.impl;

import com.rtaitai.springbootmall.dao.ProductDao;
import com.rtaitai.springbootmall.dto.ProductQueryParams;
import com.rtaitai.springbootmall.dto.ProductRequest;
import com.rtaitai.springbootmall.entity.Product;
import com.rtaitai.springbootmall.mapper.ProductMapper;
import com.rtaitai.springbootmall.repository.ProductRepository;
import com.rtaitai.springbootmall.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProductServiceimpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {

        return productMapper.countProduct(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productMapper.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findProductByProductId(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        productMapper.createProduct(product);
        return product.getProductId();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        Product product = productRepository.findProductByProductId(productId);
        product.setProductName(productRequest.getProductName());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setLastModifiedDate(LocalDateTime.now());
        productRepository.save(product);

    }

    @Override
    @Transactional
    public void deleteProductById(Integer productId) {
        productRepository.deleteProductByProductId(productId);
    }
}
