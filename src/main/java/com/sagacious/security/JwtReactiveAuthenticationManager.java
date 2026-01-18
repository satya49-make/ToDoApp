package com.sagacious.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public JwtReactiveAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Mono.empty();
        }
        String token = Objects.requireNonNull(authentication.getCredentials()).toString();
        try {
            Claims claims = jwtUtil.extractClaims(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            UserDetails principal = org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password("") // no password needed
                    .roles(role)
                    .build();

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    Collections.singleton(() -> "ROLE_" + role)
            );
            return Mono.just(auth);
        } catch (Exception e) {
            return Mono.empty(); // invalid token
        }
    }
}