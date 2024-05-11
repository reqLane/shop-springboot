package com.naukma.shopspringboot.order_product.model;

public record OrderProductRequestDTO(
        Long productId,
        Integer amount,
        Long colorId,
        Long materialId
) {
}
