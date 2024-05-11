package com.naukma.shopspringboot.order.model;

import com.naukma.shopspringboot.order_product.model.OrderProductRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        Long userId,
        List<OrderProductRequestDTO> orderProducts,
        BigDecimal price
) {
}
