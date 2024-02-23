package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.dto.CreateOrderRequest;
import com.rtaitai.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
