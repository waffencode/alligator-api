package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('ROLE_BUSINESS_ANALYTIC')")
public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {
}