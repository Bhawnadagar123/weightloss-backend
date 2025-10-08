package com.weightloss.weightloss_backend.config;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.weightloss.weightloss_backend.entities.AuthResponse;
import com.weightloss.weightloss_backend.entities.LoginRequest;
import com.weightloss.weightloss_backend.entities.RegisterRequest;
import com.weightloss.weightloss_backend.entities.Role;
import com.weightloss.weightloss_backend.entities.User;
import com.weightloss.weightloss_backend.repository.UserRepository;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        User u = new User();
        u.setEmail(req.getEmail());
        u.setName(req.getName());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRoles(Set.of(Role.ROLE_USER)); // default role
        userRepository.save(u);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        var userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        AuthResponse resp = new AuthResponse();
        resp.setAccessToken(token);
        resp.setExpiresInMs(Long.parseLong(System.getProperty("jwt.expiration-ms", "3600000")));
        return ResponseEntity.ok(resp);
    }
}
