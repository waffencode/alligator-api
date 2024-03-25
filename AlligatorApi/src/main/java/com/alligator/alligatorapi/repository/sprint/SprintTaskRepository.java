package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.SprintTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Long> {
}