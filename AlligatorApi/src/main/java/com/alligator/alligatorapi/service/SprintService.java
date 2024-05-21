package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.enums.SprintState;
import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.repository.sprint.AssignedTaskRepository;
import com.alligator.alligatorapi.model.repository.sprint.SprintTaskRepository;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintTaskRepository sprintTaskRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final AssignedTaskRepository assignedTaskRepository;
    private final TaskService taskService;
    private final SprintRepository sprintRepository;

    /**
     * Proceeds with task assignment to the sprint. Returns a list of suggested tasks.
     */
    // Signature changed for test purposes.
    public List<AssignedTask> doTaskAssignation(Sprint sprint) {
        /*
         1. Вытаскиваем таски из SprintTask в List<SprintTask>
         2. Проверка статуса задачи: назначена ли она, не завершена ли. Проверка зависимостей.
        */
        List<SprintTask> allowedToAssignationTasks = getSprintTasks(sprint);

        Team sprintTeam = sprint.getTeam();
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(sprintTeam);
        List<AssignedTask> assignedTasks = new ArrayList<>();

        // First solution: assign all tasks to all team members.
        for (TeamMember teamMember : teamMembers) {
            for (SprintTask task : allowedToAssignationTasks) {
                AssignedTask assignedTask = new AssignedTask();
                assignedTask.setTask(task);
                assignedTask.setTeamMember(teamMember);
                assignedTaskRepository.save(assignedTask);
                assignedTasks.add(assignedTask);
            }
        }

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

        // For test purposes.
        return assignedTasks;
    }

    // Added for further decomposition.
    public List<SprintTask> getSprintTasks(Sprint sprint)
    {
        return sprintTaskRepository.findAllBySprint(sprint).stream()
                .filter(task -> task.getTask().getState().equals(TaskState.TODO))
                .filter(task -> taskService.taskHasUndoneDependencies(task.getTask()))
                .sorted(Comparator.comparing(task -> task.getTask().getPriority()))
                .toList();
    }

    /**
     * Rules:
     * 1) only one active sprint per team
     * @param sprint
     * @return
     */
    public void validateSprintSave(Sprint sprint) {
        // 1
        if(sprint.getState() == SprintState.ACTIVE) {
            Team team = sprint.getTeam();
            List<Sprint> teamSprints = sprintRepository.findAllByTeam(team);

            for(Sprint s : teamSprints) {
                if (s.getState() == SprintState.ACTIVE)
                    throw new IllegalStateException("Only one sprint can be active in team. Currently active sprint name: " + sprint.getName());
            }
        }
    }
}
