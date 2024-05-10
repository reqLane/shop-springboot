package com.naukma.shopspringboot.category.model;

import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @Column(nullable = false)
    private byte[] picture;

    @OneToMany(mappedBy = "category")
    private Set<Subcategory> subcategories = new HashSet<>();

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int trendingIndex() {
        int index = 0;
        for (Subcategory subcategory : subcategories) {
            for (Product product : subcategory.getProducts()) {
                index += product.trendingIndex();
            }
        }
        return index;
    }
}
