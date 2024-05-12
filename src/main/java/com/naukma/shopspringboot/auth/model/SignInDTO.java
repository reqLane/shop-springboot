package com.naukma.shopspringboot.auth.model;

public record SignInDTO(
        String email,
        String password
) {
}
