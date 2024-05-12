package com.naukma.shopspringboot.order.model;

import com.naukma.shopspringboot.order.status.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record OrderDTO(
        Long orderId,
        Integer productsCount,
        Timestamp orderDate,
        BigDecimal price,
        OrderStatus status
) {
}
