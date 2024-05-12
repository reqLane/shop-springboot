package com.naukma.shopspringboot.user;

import com.naukma.shopspringboot.auth.AuthService;
import com.naukma.shopspringboot.order.model.OrderDTO;
import com.naukma.shopspringboot.user.model.ChangePasswordDTO;
import com.naukma.shopspringboot.user.model.EditUserProfileDTO;
import com.naukma.shopspringboot.user.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        if (!userId.equals(authService.getCurrentUserId()))
            throw new BadCredentialsException("UNAUTHORIZED - GETTING USER");

        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/{userId}/profile-edit")
    public ResponseEntity<UserDTO> editUserProfile(@PathVariable("userId") Long userId, @RequestBody EditUserProfileDTO body) {
        if (!userId.equals(authService.getCurrentUserId()))
            throw new BadCredentialsException("UNAUTHORIZED - EDITING PROFILE");

        return ResponseEntity.ok(userService.editUserProfile(userId, body));
    }

    @PatchMapping("/{userId}/password-change")
    public ResponseEntity<String> changeUserPassword(@PathVariable("userId") Long userId, @RequestBody ChangePasswordDTO body) {
        if (!userId.equals(authService.getCurrentUserId()))
            throw new BadCredentialsException("UNAUTHORIZED - CHANGING PASSWORD");

        userService.changeUserPassword(userId, body);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable("userId") Long userId) {
        if (!userId.equals(authService.getCurrentUserId()))
            throw new BadCredentialsException("UNAUTHORIZED - GETTING ORDERS OF USER");

        return ResponseEntity.ok(userService.getUserOrders(userId));
    }
}
