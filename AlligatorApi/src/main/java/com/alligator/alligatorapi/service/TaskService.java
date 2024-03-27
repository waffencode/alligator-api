package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.repository.sprint.SprintTaskRepository;
import com.alligator.alligatorapi.repository.team.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final SprintTaskRepository sprintTaskRepository;
    private final TeamMemberRepository teamMemberRepository;

    public List<AssignedTask> suggestTaskAssignation() {
        Logger.getLogger(TaskService.class.getName()).info("This doesn't works yet...");
        return new ArrayList<>();
    }
}
