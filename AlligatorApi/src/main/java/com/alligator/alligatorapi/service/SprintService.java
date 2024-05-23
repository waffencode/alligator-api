package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.enums.SprintState;
import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.repository.sprint.AssignedTaskRepository;
import com.alligator.alligatorapi.model.repository.sprint.SprintRepository;
import com.alligator.alligatorapi.model.repository.sprint.SprintTaskRepository;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRepository;
import com.alligator.alligatorapi.model.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SprintService {
    private final UserRepository userRepository;
    private final SprintTaskRepository sprintTaskRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final AssignedTaskRepository assignedTaskRepository;
    private final TaskService taskService;
    private final SprintRepository sprintRepository;

    /**
     * Proceeds with task assignment to the sprint.
     *
     * @param sprint the Sprint object to which tasks will be assigned.
     * @return a list of AssignedTask objects representing the tasks assigned to team members.
     */
    // TODO: Make method return `void` or remove side effects.
    public List<AssignedTask> doTaskAssignation(Sprint sprint) {
        /*
         1. Вытаскиваем таски из SprintTask в List<SprintTask>
         2. Проверка статуса задачи: назначена ли она, не завершена ли. Проверка зависимостей.
        */
        log.info("Assigning tasks to sprint: {}", sprint);
        List<SprintTask> allowedToAssignationTasks = getSprintTasks(sprint);
        log.info("allowedToAssignationTasks count: {}", allowedToAssignationTasks.size());

        Team sprintTeam = sprint.getTeam();
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(sprintTeam);
        List<AssignedTask> assignedTasks = new ArrayList<>();

        // First solution: assign all tasks to all team members.
        for (TeamMember teamMember : teamMembers) {
            for (SprintTask task : allowedToAssignationTasks) {
                log.info("task: {}, teamMember: {}", task, teamMember);
                AssignedTask assignedTask = new AssignedTask();
                assignedTask.setTask(task);
                assignedTask.setTeamMember(teamMember);
                assignedTaskRepository.save(assignedTask);
                log.info("assignedTask: {}", assignedTask);
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
         4. Сопоставление по возможностям ролей
         (у юзера может быть несколько ролей)
        */

        log.info("Assigned tasks: {}", assignedTasks);

        return assignedTasks;
    }

    public List<SprintTask> getSprintTasks(Sprint sprint)
    {
        return sprintTaskRepository.findAllBySprint(sprint).stream()
                .filter(task -> task.getTask().getState().equals(TaskState.TODO))
                .filter(task -> !taskService.taskHasUndoneDependencies(task.getTask()))
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
        if (sprint.getState() == SprintState.ACTIVE) {
            Team team = sprint.getTeam();
            List<Sprint> teamSprints = sprintRepository.findAllByTeam(team);

            for (Sprint teamSprint : teamSprints) {
                if (teamSprint.getState() == SprintState.ACTIVE)
                    throw new IllegalStateException("Only one sprint can be active in team. Currently active sprint name: " + sprint.getName());
            }
        }
    }

    public List<Sprint> findAllWhereUserIsTeamMemberByUserId(Long userId) {
        List<Sprint> sprints = new ArrayList<>();

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        List<TeamMember> userTeamMembers = teamMemberRepository.findAllByUser(user);

        if(userTeamMembers.isEmpty()) return sprints;

        userTeamMembers.forEach(teamMember -> sprints.addAll(sprintRepository.findAllByTeam(teamMember.getTeam())));

        return sprints;
    }
}
