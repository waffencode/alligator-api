package com.alligator.alligatorapi.model.repository.sprint;

import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.sprint.SprintTaskRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SprintTaskRequiredRoleRepository extends JpaRepository<SprintTaskRole, Long>, QuerydslPredicateExecutor<SprintTaskRole> {
    List<SprintTaskRole> findByTask(SprintTask task);
}