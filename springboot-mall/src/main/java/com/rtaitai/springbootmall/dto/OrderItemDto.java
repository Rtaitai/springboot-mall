package com.rtaitai.springbootmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    Integer orderItemId;

    Integer orderId;

    Integer productId;

    Integer quantity;

    Integer amount;

    String productName;

    String imageUrl;

}
