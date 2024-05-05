package com.naukma.shopspringboot.color.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, length = 6)
    private String hexCode;
}
