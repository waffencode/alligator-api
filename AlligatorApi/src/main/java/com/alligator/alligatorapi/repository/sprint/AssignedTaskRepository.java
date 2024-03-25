package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTaskRepository extends JpaRepository<AssignedTask, Long> {
}