package com.naukma.shopspringboot.subcategory.model;

import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "subcategory")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subcategoryId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private List<Product> products;
}
