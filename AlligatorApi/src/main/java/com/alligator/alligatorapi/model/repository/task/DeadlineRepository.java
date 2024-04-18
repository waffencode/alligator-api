package com.alligator.alligatorapi.model.repository.task;

import com.alligator.alligatorapi.model.entity.task.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}