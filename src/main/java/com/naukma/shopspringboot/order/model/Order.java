package com.naukma.shopspringboot.order.model;

import com.naukma.shopspringboot.order.status.OrderStatus;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public Order(User user) {
        this.user = user;
    }

    public Order(BigDecimal price, User user) {
        this.price = price;
        this.user = user;
    }

    public void calculatePrice() {
        if (price == null) {
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderProduct orderProduct : orderProducts) {
                totalPrice = totalPrice.add(orderProduct.getProduct().getPrice().multiply(new BigDecimal(orderProduct.getAmount())));
            }
            setPrice(totalPrice);
        }
    }

    @PrePersist
    public void prePersist() {
        if (orderDate == null) {
            setOrderDate(new Timestamp(System.currentTimeMillis()));
        }
        if (status == null) {
            setStatus(OrderStatus.PENDING);
        }
    }
}
