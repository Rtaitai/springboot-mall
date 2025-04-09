package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.request.CreateOrderRequest;
import com.rtaitai.springbootmall.dto.OrderQueryParams;
import com.rtaitai.springbootmall.entity.Order;
import com.rtaitai.springbootmall.response.OrderResponse;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    OrderResponse getOrderById(Integer orderId);

    List<Order> getOrdersByUserId(Integer userId);
}
