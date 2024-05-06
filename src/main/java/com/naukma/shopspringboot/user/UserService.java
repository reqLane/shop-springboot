package com.naukma.shopspringboot.user;

import com.naukma.shopspringboot.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            result.add(user);
        }
        return result;
    }

    public User findById(Long id) {
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
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
