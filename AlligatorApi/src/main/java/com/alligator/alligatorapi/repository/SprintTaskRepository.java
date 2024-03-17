package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.SprintTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Long> {
}