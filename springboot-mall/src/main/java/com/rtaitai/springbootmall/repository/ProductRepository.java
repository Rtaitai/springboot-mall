package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByProductId(Integer productId);

    @Modifying
    @Transactional
    @Query(value ="UPDATE product SET stock = :stock, last_modified_date = now() WHERE product_id = :productId", nativeQuery = true)
    int updateStockByProductId(@Param("productId") Integer productId, @Param("stock") Integer stock);

    void deleteProductByProductId(Integer productId);
}
