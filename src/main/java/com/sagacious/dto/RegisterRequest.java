package com.sagacious.dto;

import com.sagacious.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private Role role; // "USER" or "ADMIN"
}