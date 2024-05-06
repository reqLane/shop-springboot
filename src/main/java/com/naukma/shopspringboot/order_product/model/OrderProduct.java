package com.naukma.shopspringboot.order_product.model;

import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.upholstery.model.Upholstery;
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
    @JoinColumn(name = "colorId")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "upholsteryId")
    private Upholstery upholstery;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;
}
