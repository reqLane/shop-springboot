package com.naukma.shopspringboot.material.model;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
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
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "material")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @ManyToMany(mappedBy = "materials")
    private Set<Product> products = new HashSet<>();

    public Material(String name) {
        this.name = name;
    }
}
