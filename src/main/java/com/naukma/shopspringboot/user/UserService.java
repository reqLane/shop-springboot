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

    private Set<User> findAll() {
        Set<User> result = new HashSet<>();
        for (User user : userRepo.findAll()) {
            result.add(user);
        }
        return result;
    }

    private User findById(Long id) {
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private User create(User user) {
        return userRepo.save(user);
    }

    private void update(User user) {
        userRepo.save(user);
    }

    private void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    private void delete(User user) {
        userRepo.deleteById(user.getUserId());
    }

    private void deleteAll() {
        userRepo.deleteAll();
    }
}
