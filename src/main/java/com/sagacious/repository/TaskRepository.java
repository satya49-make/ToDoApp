package com.sagacious.repository;

import com.sagacious.entity.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
    Flux<Task> findByUsername(String username);

}