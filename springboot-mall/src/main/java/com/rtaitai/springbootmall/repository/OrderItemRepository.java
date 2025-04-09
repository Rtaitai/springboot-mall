package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query(value = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url FROM order_item oi LEFT JOIN product p ON oi.product_id = p.product_id WHERE oi.order_id = :orderId", nativeQuery = true)
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);

}
