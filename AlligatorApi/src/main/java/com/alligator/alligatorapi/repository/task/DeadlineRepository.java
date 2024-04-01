package com.alligator.alligatorapi.repository.task;

import com.alligator.alligatorapi.entity.task.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}