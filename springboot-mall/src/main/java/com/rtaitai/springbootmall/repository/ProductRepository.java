package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductByProductId(Integer productId);
}
