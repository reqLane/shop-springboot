package com.naukma.shopspringboot.color.model;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, length = 6)
    @Pattern(regexp = "^[A-Za-z\\d]{6}$")
    private String hexCode;

    @OneToMany(mappedBy = "color")
    private Set<OrderProduct> orderProducts;

    @ManyToMany(mappedBy = "colors")
    private Set<Product> products;
}
