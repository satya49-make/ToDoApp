package com.sagacious.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sagacious.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
//@JsonPropertyOrder({ "id", "title", "description", "status", "progress", "createdAt", "updatedAt" })
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private int progress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}