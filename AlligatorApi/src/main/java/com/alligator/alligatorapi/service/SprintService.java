package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.repository.sprint.SprintTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintTaskRepository sprintTaskRepository;
    private final TaskService taskService;

    public List<AssignedTask> suggestTaskAssignation(Sprint sprint) {
        /*
         1. Вытаскиваем таски из SprintTask в List<SprintTask>
         2. Проверка статуса задачи: назначена ли она, не завершена ли. Проверка зависимостей.
        */
        List<SprintTask> allowedToAssignationTasks = sprintTaskRepository.findAllBySprint(sprint).stream()
                .filter(task -> task.getTask().getState().equals(TaskState.TODO))
                .filter(task -> taskService.taskHasUndoneDependencies(task.getTask()))
                .sorted(Comparator.comparing(task -> task.getTask().getPriority()))
                .toList();

        /*
         3. Сортировка по приоритетам и дедлайнам
         (матрица Эйзенхауэра. Если задачи связанные, то рассматриваем по самой важной и срочной в цепочке)
         Приоритеты: A, B, C, D, E.
         Если дедлайн задачи истекает посреди спринта, Тогда выполняется в первую очередь. Hard deadline - по истечение дедлайна задача не имеет смысл
         Мягкий дедлайн - разные варианты, например, снижается приоритет.
         Будем ли добавлять в качестве характеристики задачи время на выполнение?
        */


        /*
         4. Сопоставление по возможностям ролей
         (у юзера может быть несколько ролей)
        */


        return null;
    }
}
