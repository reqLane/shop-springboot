package com.naukma.shopspringboot.auth.model;

import com.naukma.shopspringboot.user.model.UserDTO;

public record JWTTokenDTO(
        String jwt,
        UserDTO user
) {
}
