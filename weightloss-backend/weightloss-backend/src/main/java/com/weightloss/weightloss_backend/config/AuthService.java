//package com.weightloss.weightloss_backend.config;
//import com.weightloss.weightloss_backend.entities.Role;
//import com.weightloss.weightloss_backend.entities.User;
//import com.weightloss.weightloss_backend.repository.UserRepository;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//@Service
//public class AuthService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
//    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    public User register(String name, String email, String rawPassword) {
//        if (userRepository.existsByEmail(email)) {
//            throw new RuntimeException("Email already in use");
//        }
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(rawPassword));
//        user.setRoles(Set.of(Role.ROLE_USER));
//        return userRepository.save(user);
//    }
//
//    public String login(String email, String rawPassword) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//
//        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        var roles = user.getRoles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet());
//        return jwtUtil.generateToken(email, roles);
//    }
//}
// 