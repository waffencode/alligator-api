package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.SprintTask;
import com.alligator.alligatorapi.entity.sprint.SprintTaskRequiredRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SprintTaskRepository extends JpaRepository<SprintTask, Long> {
    @PreAuthorize("@securityService.validatePrincipalIsScrumMasterOfSprint(sprintTask.sprint)")
    <S extends SprintTask> S save(S sprintTask);

    @PreAuthorize("false")
    <S extends SprintTask> List<S> saveAll(Iterable<S> sprintTask);
}