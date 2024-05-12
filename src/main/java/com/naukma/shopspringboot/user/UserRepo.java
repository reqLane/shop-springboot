package com.naukma.shopspringboot.user;

import com.naukma.shopspringboot.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByPhoneEquals(String phone);

    Optional<User> findByEmailEquals(String email);
}
