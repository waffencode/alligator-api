package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}