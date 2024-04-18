package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.repository.sprint.SprintTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintTaskRepository sprintTaskRepository;
    private final TaskService taskService;

    public List<AssignedTask> suggestTaskAssignation(Sprint sprint) {
        List<SprintTask> allowedToAssignationTasks = sprintTaskRepository.findAllBySprint(sprint).stream()
                .filter(task -> task.getTask().getState().equals(TaskState.TODO))
                .filter(task -> taskService.taskHasUndoneDependencies(task.getTask()))
                .toList();

        return null;
    }
}
