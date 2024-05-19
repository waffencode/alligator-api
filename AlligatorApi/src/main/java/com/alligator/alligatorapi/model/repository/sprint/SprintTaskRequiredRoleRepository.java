package com.alligator.alligatorapi.model.repository.sprint;

import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.sprint.SprintTaskRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintTaskRequiredRoleRepository extends JpaRepository<SprintTaskRole, Long> {
    List<SprintTaskRole> findByTask(SprintTask task);
}