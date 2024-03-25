package com.alligator.alligatorapi.repository.task;

import com.alligator.alligatorapi.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TaskRepository extends JpaRepository<Task, Long> {
}