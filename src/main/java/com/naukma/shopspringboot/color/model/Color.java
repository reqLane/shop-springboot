package com.naukma.shopspringboot.color.model;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, length = 6)
    @Pattern(regexp = "^[A-Za-z\\d]{6}$")
    private String hexCode;

    @OneToMany(mappedBy = "color")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @ManyToMany(mappedBy = "colors")
    private Set<Product> products = new HashSet<>();

    public Color(String name, String hexCode) {
        this.name = name;
        this.hexCode = hexCode;
    }
}
