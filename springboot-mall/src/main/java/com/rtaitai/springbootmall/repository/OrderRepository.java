package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.dto.OrderQueryParams;
import com.rtaitai.springbootmall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderByOrderId(Integer orderId);

    @Query(value = "SELECT * FROM `order` WHERE user_id = :userId", nativeQuery = true)
    List<Order> findOrderByUserId(Integer userId);

    @Query(value = "SELECT count(*) FROM `order` WHERE user_id = :userId", nativeQuery = true)
    Integer countOrder(Integer userId);

}
