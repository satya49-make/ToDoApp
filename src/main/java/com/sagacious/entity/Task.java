package com.sagacious.entity;

import com.sagacious.enums.TaskStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private TaskStatus status;   // use enum instead of String
    private String username;     // owner of the task (linked to JWT user)
    private int progress;        // percentage 0–100
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}