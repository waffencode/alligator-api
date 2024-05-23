package com.alligator.alligatorapi.model.repository.sprint;

import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Long>, QuerydslPredicateExecutor<SprintTask> {
    List<SprintTask> findAllBySprint(Sprint sprint);
}