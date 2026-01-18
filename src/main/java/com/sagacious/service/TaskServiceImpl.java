package com.sagacious.service;

import com.sagacious.entity.Task;
import com.sagacious.enums.TaskStatus;
import com.sagacious.repository.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Task> createTask(Task task, String username) {
        task.setUsername(username);
        task.setStatus(TaskStatus.PENDING);
        task.setProgress(0);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        log.info("user task{} ", task);
        return taskRepository.save(task);
    }

    @Override
    public Flux<Task> getTasksForUser(String username) {
        return taskRepository.findByUsername(username);
    }

    @Override
    public Mono<Task> updateTask(String id, Task updatedTask, String username) {
        return taskRepository.findById(id)
                .filter(existing -> existing.getUsername().equals(username))
                .flatMap(existing -> {
                    existing.setTitle(updatedTask.getTitle());
                    existing.setDescription(updatedTask.getDescription());
                    existing.setStatus(updatedTask.getStatus());
                    existing.setProgress(updatedTask.getProgress());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return taskRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteTask(String id, String username) {
        return taskRepository.findById(id)
                .filter(existing -> existing.getUsername().equals(username))
                .flatMap(taskRepository::delete);
    }

    @Override
    public Mono<Map<String, List<Task>>> getAllTasksGroupedByUser() {
        return taskRepository.findAll()
                .collectList()
                .map(tasks -> tasks.stream()
                        .collect(Collectors.groupingBy(Task::getUsername)));
    }
}