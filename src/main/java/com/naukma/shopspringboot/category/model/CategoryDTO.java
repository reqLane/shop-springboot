package com.naukma.shopspringboot.category.model;

public record CategoryDTO(
        Long categoryId,
        String name,
        String description
) {
}
