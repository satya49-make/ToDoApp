package com.sagacious.controller;

import com.sagacious.dto.TaskRequest;
import com.sagacious.entity.Task;
import com.sagacious.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@Log4j2
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //  Get all tasks for logged-in user
    @GetMapping
    public Flux<Task> getUserTasks(@AuthenticationPrincipal(expression = "username") String username) {
        return taskService.getTasksForUser(username);
    }

    //  Create a new task for logged-in user
    @PostMapping
    public Mono<Task> createTask(@AuthenticationPrincipal(expression = "username") String username,
                                 @RequestBody TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        log.info("Task request {}",request);
        return taskService.createTask(task, username);
    }

    //  Update a task (only owner can update)
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@AuthenticationPrincipal(expression = "username") String username,
                                 @PathVariable String id,
                                 @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask, username);
    }

    //  Delete a task (only owner can delete)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@AuthenticationPrincipal(expression = "username") String username,
                                 @PathVariable String id) {
        return taskService.deleteTask(id, username);
    }

    // Admin: Get all tasks grouped by user
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Map<String, List<Task>>> getAllTasksGroupedByUser() {
        return taskService.getAllTasksGroupedByUser();
    }
}