package com.sagacious.service;

import com.sagacious.dto.TaskRequest;
import com.sagacious.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<Task> createTask(TaskRequest request);
    Flux<Task> getAllTasks();
    Mono<Task> getTaskById(String id);
    Mono<Task> updateTask(String id, TaskRequest request);
    Mono<Task> updateProgress(String id, int progress);
    Mono<Void> deleteTask(String id);
    Flux<Task> streamTasks();
}