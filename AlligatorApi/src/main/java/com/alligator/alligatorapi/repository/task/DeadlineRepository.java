package com.alligator.alligatorapi.repository.task;

import com.alligator.alligatorapi.entity.task.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}