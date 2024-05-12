package com.naukma.shopspringboot.auth.model;

public record SignUpDTO(
        String name,
        String surname,
        String phone,
        String email,
        String password
) {
}
