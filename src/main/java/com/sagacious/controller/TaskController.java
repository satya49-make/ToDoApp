package com.sagacious.controller;

import com.sagacious.dto.TaskRequest;
import com.sagacious.dto.TaskResponse;
import com.sagacious.entity.Task;
import com.sagacious.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // ✅ Create Task
    @PostMapping
    public Mono<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return service.createTask(request).map(this::toResponse);
    }

    // ✅ Get All Tasks
    @GetMapping
    public Flux<TaskResponse> getAllTasks() {
        return service.getAllTasks().map(this::toResponse);
    }

    // ✅ Get Task by ID
    @GetMapping("/{id}")
    public Mono<TaskResponse> getTaskById(@PathVariable String id) {
        return service.getTaskById(id).map(this::toResponse);
    }

    // ✅ Update Task (title/description)
    @PutMapping("/{id}")
    public Mono<TaskResponse> updateTask(@PathVariable String id, @Valid @RequestBody TaskRequest request) {
        return service.updateTask(id, request).map(this::toResponse);
    }

    // ✅ Update Progress (partial update)
    @PatchMapping("/{id}/progress/{progress}")
    public Mono<TaskResponse> updateProgress(@PathVariable String id, @PathVariable int progress) {
        return service.updateProgress(id, progress).map(this::toResponse);
    }

    // ✅ Delete Task
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable String id) {
        return service.deleteTask(id);
    }

    // ✅ Real-time Stream (SSE)
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TaskResponse> streamTasks() {
        return service.streamTasks().map(this::toResponse);
    }

    // 🔄 Mapper: Entity → Response DTO
    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .progress(task.getProgress())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}