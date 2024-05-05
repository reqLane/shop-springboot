package com.naukma.shopspringboot.user.model;

import com.naukma.shopspringboot.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(nullable = false, length = 16)
    private String surname;

    @Column(nullable = false, length = 16)
    private String fathername;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    private String phone;

    private Date birthdate;

    @Column(nullable = false, length = 72)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
