package com.naukma.shopspringboot.product.model;

import java.math.BigDecimal;
import java.util.List;

public record FilteredProductsRequestDTO(
        Long categoryId,
        Long subcategoryId,
        String search,
        BigDecimal priceMin,
        BigDecimal priceMax,
        List<Long> materialIds,
        List<Long> colorIds
) {
}
