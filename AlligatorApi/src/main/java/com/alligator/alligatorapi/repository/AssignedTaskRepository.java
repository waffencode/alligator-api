package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.AssignedTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTaskRepository extends JpaRepository<AssignedTask, Long> {
}