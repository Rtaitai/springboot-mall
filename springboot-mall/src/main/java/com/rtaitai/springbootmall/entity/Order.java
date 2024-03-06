package com.rtaitai.springbootmall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer userId;

    private Integer totalAmount;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private static final long serialVersionUID = 1L;
}
