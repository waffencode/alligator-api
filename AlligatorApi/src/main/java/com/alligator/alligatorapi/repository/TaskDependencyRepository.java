package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {
}