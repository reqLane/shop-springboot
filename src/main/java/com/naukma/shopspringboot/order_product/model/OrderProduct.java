package com.naukma.shopspringboot.order_product.model;

import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.material.model.Material;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_product")
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
}
