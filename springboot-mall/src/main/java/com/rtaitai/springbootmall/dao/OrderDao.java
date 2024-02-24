package com.rtaitai.springbootmall.dao;

import com.rtaitai.springbootmall.dto.OrderQueryParams;
import com.rtaitai.springbootmall.model.Order;
import com.rtaitai.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders (OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
