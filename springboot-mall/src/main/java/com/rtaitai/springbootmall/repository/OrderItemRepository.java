package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.entity.OrderItem;
import com.rtaitai.springbootmall.dto.OrderItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("SELECT new com.rtaitai.springbootmall.dto.OrderItemDto(oi.orderItemId, oi.orderId, oi.productId, oi.quantity, oi.amount, p.productName, p.imageUrl) FROM OrderItem oi LEFT JOIN Product p ON oi.productId = p.productId WHERE oi.orderId = :orderId")
    List<OrderItemDto> findOrderItemsByOrderId(@Param("orderId") Integer orderId);



}
