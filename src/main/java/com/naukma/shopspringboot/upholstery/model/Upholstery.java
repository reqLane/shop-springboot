package com.naukma.shopspringboot.upholstery.model;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "upholstery")
public class Upholstery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upholsteryId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "upholstery")
    private Set<OrderProduct> orderProducts;

    @ManyToMany(mappedBy = "upholsteries")
    private Set<Product> products;
}
