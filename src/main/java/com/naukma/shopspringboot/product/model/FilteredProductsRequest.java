package com.naukma.shopspringboot.product.model;

import java.math.BigDecimal;
import java.util.List;

public record FilteredProductsRequest(
        String categoryName,
        String subcategoryName,
        String search,
        BigDecimal priceMin,
        BigDecimal priceMax,
        List<String> materials,
        List<String> colors
) {
}
