package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.SprintTask;
import com.alligator.alligatorapi.entity.sprint.SprintTaskRequiredRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Long> {}