package com.rtaitai.springbootmall.service.impl;

import com.rtaitai.springbootmall.dao.OrderDao;
import com.rtaitai.springbootmall.dao.ProductDao;
import com.rtaitai.springbootmall.dto.BuyItem;
import com.rtaitai.springbootmall.entity.User;
import com.rtaitai.springbootmall.repository.OrderItemRepository;
import com.rtaitai.springbootmall.repository.UserRepository;
import com.rtaitai.springbootmall.request.CreateOrderRequest;
import com.rtaitai.springbootmall.dto.OrderQueryParams;
import com.rtaitai.springbootmall.entity.Order;
import com.rtaitai.springbootmall.entity.OrderItem;
import com.rtaitai.springbootmall.model.Product;
import com.rtaitai.springbootmall.repository.OrderRepository;
import com.rtaitai.springbootmall.response.OrderResponse;
import com.rtaitai.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {

        return orderRepository.countOrder(orderQueryParams.getUserId());

    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        List<Order> orderList = orderRepository.findOrderByUserId(orderQueryParams.getUserId());

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderItemRepository.findOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //檢查 user 是否存在
        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            Product product = productDao.getProductById(buyItem.getProductId());


            //檢查 product 是否存在、庫存是否足夠
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()){
                log.warn("商品 {} 庫存量不夠，無法購買。剩餘庫存 {} ，欲購買數量 {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }


        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public OrderResponse getOrderById(Integer orderId) {

        Order order = orderRepository.findOrderByOrderId(orderId);

        List<OrderItem> orderItemList = orderItemRepository.findOrderItemsByOrderId(orderId);

        return OrderResponse
                .builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .createdDate(order.getCreatedDate())
                .lastModifiedDate(order.getLastModifiedDate())
                .orderItemList(orderItemList)
                .build();
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findOrderByUserId(userId);
    }
}
