package com.sagacious.controller;

import com.sagacious.dto.LoginRequest;
import com.sagacious.dto.RegisterRequest;
import com.sagacious.entity.User;
import com.sagacious.repository.UserRepository;
import com.sagacious.security.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Mono<String> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole((request.getRole()));
        return userRepository.save(user)
                .map(saved -> "User registered successfully with id: " + saved.getId());
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .flatMap(user -> {
                    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
                        log.info("token generated!!");
                        return Mono.just(token);
                    } else {
                        return Mono.error(new RuntimeException("Invalid credentials"));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}