package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.SprintTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Long> {
    List<SprintTask> findAllBySprint(Sprint sprint);
}