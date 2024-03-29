package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.entity.task.Task;
import com.alligator.alligatorapi.entity.team.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTaskRepository extends JpaRepository<AssignedTask, Long> {
    Boolean existsByTaskAndTeamMember(Task task, TeamMember member);
}