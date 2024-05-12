package com.naukma.shopspringboot.user.model;

public record SignUpDTO(
        String name,
        String surname,
        String email,
        String password
) {
}
