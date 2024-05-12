package com.naukma.shopspringboot.user;

import com.naukma.shopspringboot.user.model.ChangePasswordDTO;
import com.naukma.shopspringboot.user.model.EditUserProfileDTO;
import com.naukma.shopspringboot.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/{userId}/profile-edit")
    public ResponseEntity<UserDTO> editUserProfile(@PathVariable("userId") Long userId, @RequestBody EditUserProfileDTO body) {
        return ResponseEntity.ok(userService.editUserProfile(userId, body));
    }

    @PatchMapping("/{userId}/password-change")
    public ResponseEntity<String> changeUserPassword(@PathVariable("userId") Long userId, @RequestBody ChangePasswordDTO body) {
        userService.changeUserPassword(userId, body);
        return ResponseEntity.ok("Success");
    }
}
