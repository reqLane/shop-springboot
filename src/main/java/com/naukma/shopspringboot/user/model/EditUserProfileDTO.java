package com.naukma.shopspringboot.user.model;

import java.sql.Date;

public record EditUserProfileDTO(
        String name,
        String surname,
        String fathername,
        String phone,
        String email,
        Date birthdate
) {
}
