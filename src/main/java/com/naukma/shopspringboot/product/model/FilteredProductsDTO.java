package com.naukma.shopspringboot.product.model;

import com.naukma.shopspringboot.color.model.ColorDTO;
import com.naukma.shopspringboot.material.model.MaterialDTO;

import java.util.List;

public record FilteredProductsDTO(
        List<ProductDTO> products,
        List<ColorDTO> colors,
        List<MaterialDTO> materials,
        Integer priceLow,
        Integer priceHigh
) {
}
