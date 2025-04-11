package com.rtaitai.springbootmall.entity;

import com.rtaitai.springbootmall.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "`order`")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer userId;

    private Integer totalAmount;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @Transient
    private List<OrderItemDto> orderItemList;

    private static final long serialVersionUID = 1L;
}
