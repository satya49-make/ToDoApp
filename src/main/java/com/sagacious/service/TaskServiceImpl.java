package com.sagacious.service;

import com.sagacious.dto.TaskRequest;
import com.sagacious.entity.Task;
import com.sagacious.enums.TaskStatus;
import com.sagacious.exception.ResourceNotFoundException;
import com.sagacious.repository.TaskRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Task> createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setProgress(0);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return repository.save(task);
    }

    @Override
    public Flux<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public Mono<Task> getTaskById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Task not found with id: " + id)));
    }

    @Override
    public Mono<Task> updateTask(String id, TaskRequest request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Task not found with id: " + id)))
                .flatMap(task -> {
                    task.setTitle(request.getTitle());
                    task.setDescription(request.getDescription());
                    task.setUpdatedAt(LocalDateTime.now());
                    return repository.save(task);
                });
    }

    @Override
    public Mono<Task> updateProgress(String id, int progress) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Task not found with id: " + id)))
                .flatMap(task -> {
                    task.setProgress(progress);
                    task.setStatus(progress == 100 ? TaskStatus.COMPLETED : TaskStatus.IN_PROGRESS);
                    task.setUpdatedAt(LocalDateTime.now());
                    return repository.save(task);
                });
    }

    @Override
    public Mono<Void> deleteTask(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Task not found with id: " + id)))
                .flatMap(repository::delete);
    }

    @Override
    public Flux<Task> streamTasks() {
        return repository.findAll().repeat();
    }
}