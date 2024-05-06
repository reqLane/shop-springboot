package com.naukma.shopspringboot.order_product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class OrderProductId implements Serializable {
    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "productId")
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId other = (OrderProductId) o;
        return Objects.equals(orderId, other.getOrderId())
                && Objects.equals(productId, other.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
