package com.naukma.shopspringboot.user.model;

public record ChangePasswordDTO(
        String password,
        String newPassword
) {
}
