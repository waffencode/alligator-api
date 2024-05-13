package com.alligator.alligatorapi.model.repository.task;

import com.alligator.alligatorapi.model.entity.task.Task;
import com.alligator.alligatorapi.model.entity.task.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {
    List<TaskDependency> findAllByTask(Task task);
}