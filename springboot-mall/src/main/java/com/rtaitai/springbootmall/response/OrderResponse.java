package com.rtaitai.springbootmall.response;

import com.rtaitai.springbootmall.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Integer orderId;

    private Integer userId;

    private Integer totalAmount;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private List<OrderItem> orderItemList;
}
