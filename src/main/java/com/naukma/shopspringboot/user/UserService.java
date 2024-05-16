package com.naukma.shopspringboot.user;

import com.naukma.shopspringboot.exception.InvalidUserDataException;
import com.naukma.shopspringboot.order.model.OrderDTO;
import com.naukma.shopspringboot.user.model.*;
import com.naukma.shopspringboot.util.DTOMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import static com.naukma.shopspringboot.util.Utils.*;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // BUSINESS LOGIC

    public UserDTO getUserById(Long userId) {
        User user = findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("USER ID-%d NOT FOUND", userId)));

        return DTOMapper.toDTO(user);
    }

    public UserDTO editUserProfile(Long userId, EditUserProfileDTO request) {
        User user = findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("USER ID-%d NOT FOUND", userId)));

        if ((findByPhone(request.phone()).isPresent() && !user.getPhone().equals(request.phone()))
                || (findByEmail(request.email()).isPresent() && !user.getEmail().equals(request.email())))
            throw new InvalidUserDataException("SIGNUP PHONE OR EMAIL IS ALREADY OCCUPIED");

        if (!isNullOrEmpty(request.name()))
            user.setName(request.name());
        if (!isNullOrEmpty(request.surname()))
            user.setSurname(request.surname());
        if (!isNullOrEmpty(request.phone()))
            user.setPhone(request.phone());
        if (!isNullOrEmpty(request.email()))
            user.setEmail(request.email());

        user.setFathername(isNullOrEmpty(request.fathername()) ? null : request.fathername());
        user.setBirthdate(request.birthdate());

        return DTOMapper.toDTO(save(user));
    }

    public void changeUserPassword(Long userId, ChangePasswordDTO request) {
        User user = findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("USER ID-%d NOT FOUND", userId)));

        if(isNullOrEmpty(request.password())) throw new InvalidUserDataException("USER PASSWORD CAN'T BE EMPTY");
        if(isNullOrEmpty(request.newPassword())) throw new InvalidUserDataException("USER NEW PASSWORD CAN'T BE EMPTY");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(request.password(), user.getPassword())) throw new InvalidUserDataException("USER PASSWORD INCORRECT");

        user.setPassword(bCryptPasswordEncoder.encode(request.newPassword()));
        save(user);
    }

    public List<OrderDTO> getUserOrders(Long userId) {
        User user = findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("USER ID-%d NOT FOUND", userId)));

        return user.getOrders()
                .stream()
                .map(DTOMapper::toDTO)
                .sorted((o1, o2) -> o2.orderDate().compareTo(o1.orderDate()))
                .toList();
    }

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

    public Optional<User> findByPhone(String phone) {
        return userRepo.findByPhoneEquals(phone);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmailEquals(email);
    }

    public User save(User user) {
        return userRepo.save(user);
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
