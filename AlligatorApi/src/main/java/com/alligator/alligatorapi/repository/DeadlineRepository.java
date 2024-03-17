package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}