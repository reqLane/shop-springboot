package com.naukma.shopspringboot.auth;

import com.naukma.shopspringboot.auth.model.AvailableDTO;
import com.naukma.shopspringboot.auth.model.JWTTokenDTO;
import com.naukma.shopspringboot.auth.model.SignInDTO;
import com.naukma.shopspringboot.auth.model.SignUpDTO;
import com.naukma.shopspringboot.user.model.User;
import com.naukma.shopspringboot.user.model.UserDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("")
    public ResponseEntity<?> auth() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(body));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTTokenDTO> signin(@RequestBody SignInDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var authUser = authenticationManager.authenticate(usernamePassword);
        User authenticatedUser = (User) authUser.getPrincipal();
        var accessToken = tokenProvider.generateAccessToken(authenticatedUser);
        return ResponseEntity.ok(new JWTTokenDTO(accessToken, DTOMapper.toDTO(authenticatedUser)));
    }

    @GetMapping("/phone-available/{phone}")
    public ResponseEntity<AvailableDTO> phoneIsAvailable(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(authService.phoneIsAvailable(phone));
    }

    @GetMapping("/email-available/{email}")
    public ResponseEntity<AvailableDTO> emailIsAvailable(@PathVariable("email") String email) {
        return ResponseEntity.ok(authService.emailIsAvailable(email));
    }
}
