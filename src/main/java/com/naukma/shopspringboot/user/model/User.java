package com.naukma.shopspringboot.user.model;

import com.naukma.shopspringboot.order.model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(nullable = false, length = 16)
    private String surname;

    @Column(length = 16)
    private String fathername;

    @Column(unique = true, nullable = false, length = 50)
    @Email(message = "INVALID USER EMAIL FORMAT")
    private String email;

    @Column(unique = true, nullable = false, length = 15)
    @Pattern(regexp = "^\\d{10,15}$", message = "INVALID USER PHONE FORMAT")
    private String phone;

    private Date birthdate;

    @Column(nullable = false, length = 72)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    public User(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }
}
