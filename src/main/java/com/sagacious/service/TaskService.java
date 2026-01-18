package com.sagacious.service;

import com.sagacious.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.List;

public interface TaskService {
    Mono<Task> createTask(Task task, String username);
    Flux<Task> getTasksForUser(String username);
    Mono<Task> updateTask(String id, Task updatedTask, String username);
    Mono<Void> deleteTask(String id, String username);

    // Admin: get all tasks grouped by username
    Mono<Map<String, List<Task>>> getAllTasksGroupedByUser();
}