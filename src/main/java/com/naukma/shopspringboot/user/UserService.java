package com.naukma.shopspringboot.user;

import com.naukma.shopspringboot.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public Set<User> findAll() {
        Set<User> result = new HashSet<>();
        for (User user : userRepo.findAll()) {
            result.add(user);
        }
        return result;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public User create(User user) {
        return userRepo.save(user);
    }

    public void update(User user) {
        userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public void delete(User user) {
        userRepo.deleteById(user.getUserId());
    }

    public void deleteAll() {
        userRepo.deleteAll();
    }
}
