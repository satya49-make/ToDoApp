//package com.sagacious.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AuthManagerConfig {
//
//    private final JwtUtil jwtUtil;
//
//    public AuthManagerConfig(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Bean
//    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager() {
//        return new JwtReactiveAuthenticationManager(jwtUtil);
//    }
//}