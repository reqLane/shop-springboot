package com.naukma.shopspringboot.order_product.model;

import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.material.model.Material;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    public OrderProduct(Integer amount, Order order, Product product, Color color, Material material) {
        this.id = new OrderProductId(order.getOrderId(), product.getProductId());
        this.amount = amount;
        this.order = order;
        this.product = product;
        this.color = color;
        this.material = material;
    }
}
