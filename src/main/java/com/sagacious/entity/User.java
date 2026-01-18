package com.sagacious.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.sagacious.enums.Role;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email;
    private String username;
    private String password;
    private Role role;
}