package com.alligator.alligatorapi.model.repository.task;

import com.alligator.alligatorapi.model.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}