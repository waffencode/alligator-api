package com.alligator.alligatorapi.repository.task;

import com.alligator.alligatorapi.entity.task.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {
}