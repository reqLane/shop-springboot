package com.naukma.shopspringboot.subcategory.model;

import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.product.model.Product;
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
@Table(name = "subcategory")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subcategoryId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private Set<Product> products = new HashSet<>();

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}
