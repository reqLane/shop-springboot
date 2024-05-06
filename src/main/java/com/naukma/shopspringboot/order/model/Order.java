package com.naukma.shopspringboot.order.model;

import com.naukma.shopspringboot.order.status.OrderStatus;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Timestamp orderDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts;

    @PrePersist
    public void prePersist() {
        if (getStatus() == null) {
            setStatus(OrderStatus.PENDING);
        }
    }
}
