package com.alligator.alligatorapi.model.repository.sprint;

import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.task.Task;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AssignedTaskRepository extends JpaRepository<AssignedTask, Long>, QuerydslPredicateExecutor<AssignedTask> {
    Boolean existsByTaskAndTeamMember(Task task, TeamMember member);
}