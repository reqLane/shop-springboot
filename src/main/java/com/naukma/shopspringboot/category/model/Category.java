package com.naukma.shopspringboot.category.model;

import com.naukma.shopspringboot.subcategory.model.Subcategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Subcategory> subcategories;
}
