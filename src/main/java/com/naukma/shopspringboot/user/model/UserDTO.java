package com.naukma.shopspringboot.user.model;

import java.sql.Date;

public record UserDTO(
        Long userId,
        String name,
        String surname,
        String fathername,
        String email,
        String phone,
        Date birthdate
) {
}
