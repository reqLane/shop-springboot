package com.naukma.shopspringboot.upholstery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "upholstery")
public class Upholstery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upholsteryId;

    @Column(unique = true, nullable = false, length = 20)
    private String name;
}
