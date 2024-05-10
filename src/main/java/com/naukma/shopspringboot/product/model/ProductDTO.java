package com.naukma.shopspringboot.product.model;

import java.math.BigDecimal;

public record ProductDTO(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        int height,
        int length,
        int width,
        BigDecimal weight,
        String packageDescription
) {
}
