package com.naukma.shopspringboot.auth;

import com.naukma.shopspringboot.auth.model.AvailableDTO;
import com.naukma.shopspringboot.auth.model.SignUpDTO;
import com.naukma.shopspringboot.exception.InvalidUserDataException;
import com.naukma.shopspringboot.user.UserService;
import com.naukma.shopspringboot.user.model.User;
import com.naukma.shopspringboot.user.model.UserDTO;
import com.naukma.shopspringboot.user.role.UserRole;
import com.naukma.shopspringboot.util.DTOMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.naukma.shopspringboot.util.Utils.*;

@Service
public class AuthService implements UserDetailsService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    // BUSINESS LOGIC

    public UserDTO signUp(SignUpDTO request) {
        if (isNullOrEmpty(request.name())
                || isNullOrEmpty(request.surname())
                || isNullOrEmpty(request.phone())
                || isNullOrEmpty(request.email())
                || isNullOrEmpty(request.password()))
            throw new InvalidUserDataException("SIGNUP REQUESTED FIELD IS EMPTY");

        if (userService.findByPhone(request.phone()).isPresent() || userService.findByEmail(request.email()).isPresent())
            throw new InvalidUserDataException("SIGNUP PHONE OR EMAIL IS ALREADY OCCUPIED");

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        User user = new User(request.name(), request.surname(), request.email(), request.phone(), encryptedPassword, UserRole.USER);
        return DTOMapper.toDTO(userService.save(user));
    }

    public AvailableDTO phoneIsAvailable(String phone) {
        return new AvailableDTO(userService.findByPhone(phone).isEmpty());
    }

    public AvailableDTO emailIsAvailable(String email) {
        return new AvailableDTO(userService.findByEmail(email).isEmpty());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("USER WITH EMAIL %s NOT FOUND", email)));
    }

    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User)
            return ((User)principal).getUserId();
        return null;
    }
}
