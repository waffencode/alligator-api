package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasAuthority('ROLE_BUSINESS_ANALYTIC')")
public interface TaskRepository extends JpaRepository<Task, Long> {
}