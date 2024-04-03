package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.enums.TaskState;
import com.alligator.alligatorapi.entity.task.Task;
import com.alligator.alligatorapi.entity.task.TaskDependency;
import com.alligator.alligatorapi.repository.task.TaskDependencyRepository;
import com.alligator.alligatorapi.repository.task.TaskRepository;
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
