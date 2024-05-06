package com.naukma.shopspringboot.product.model;

import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.upholstery.model.Upholstery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, length = 300)
    private String packageDescription;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProducts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_color",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_upholstery",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "upholstery_id"))
    private Set<Upholstery> upholsteries;
}
