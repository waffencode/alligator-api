package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.task.Task;
import com.alligator.alligatorapi.model.entity.task.TaskDependency;
import com.alligator.alligatorapi.model.repository.task.TaskDependencyRepository;
import com.alligator.alligatorapi.model.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDependencyRepository taskDependencyRepository;

    public Boolean taskHasUndoneDependencies(Task task) {
        List<TaskDependency> taskDependencies = taskDependencyRepository.findAllByTask(task);
        for (TaskDependency dependency : taskDependencies) {
            if(!dependency.getDependency().getState().equals(TaskState.DONE))
                return true;
        }

        return false;
    }
}
