package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.dto.CreateOrderRequest;
import com.rtaitai.springbootmall.dto.OrderQueryParams;
import com.rtaitai.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
