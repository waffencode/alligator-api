package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.entity.enums.SprintState;
import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.sprint.SprintTaskRole;
import com.alligator.alligatorapi.model.entity.task.Deadline;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import com.alligator.alligatorapi.model.entity.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SprintService extends RepositoryDependentService {
    private final TaskService taskService;

    /**
     * Proceeds with task assignment to the sprint.
     *
     * @param sprint the Sprint object to which tasks will be assigned.
     * @return a list of AssignedTask objects representing the tasks assigned to team members.
     */
    // TODO: Make method return `void` or remove side effects, remove excessive logging.
    public List<AssignedTask> doTaskAssignation(Sprint sprint) {
        List<SprintTask> allowedToAssignationTasks = getSprintTasks(sprint);
        log.info("allowedToAssignationTasks count: {}", allowedToAssignationTasks.size());

        Team sprintTeam = sprint.getTeam();
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(sprintTeam);
        List<AssignedTask> assignedTasks = new ArrayList<>();

        // We limit the maximum number of tasks per user to the average number of available tasks per team.
        int maxTasksPerUser = (int) Math.ceil((double) allowedToAssignationTasks.size() / teamMembers.size());

        // Solution v3: assign all tasks to team members with role check and task limits.
        for (TeamMember teamMember : teamMembers) {
            int tasksPerUser = 0;

            for (SprintTask task : allowedToAssignationTasks) {
                /*
                 TODO: There is a potential situation here where two tasks need to be assigned to two users,
                 but one task gets assigned to both users while the other remains unassigned.
                 This needs to be fixed.
                */
                if (tasksPerUser >= maxTasksPerUser)
                    break;

                if (taskHasRequirementsForRole(task)) {
                    List<TeamRole> requiredRoles = sprintTaskRequiredRoleRepository.findByTask(task).stream()
                        .map(SprintTaskRole::getRole).toList();

                    List<TeamRole> teamMemberRoles = teamMemberRoleRepository.findAllByTeamMember(teamMember).stream()
                        .map(TeamMemberRole::getRole).toList();

                    if (!requiredRoles.stream().filter(teamMemberRoles::contains).toList().isEmpty())
                    {
                        AssignedTask assignedTask = assignTaskToTeamMember(teamMember, task);
                        assignedTasks.add(assignedTask);
                        tasksPerUser++;
                    }
                }
                else {
                    AssignedTask assignedTask = assignTaskToTeamMember(teamMember, task);
                    assignedTasks.add(assignedTask);
                    tasksPerUser++;
                }
            }
        }

        log.info("Assigned tasks (all entities): {}", assignedTasks.size());

        return assignedTasks;
    }

    /**
     * Retrieve tasks from SprintTask and store them in a List.
     * Check the status of each task: ensure it is not completed and has no undone dependencies.
     * Sort tasks by priority, deadline, complexity.
     */
    public List<SprintTask> getSprintTasks(Sprint sprint) {
        return sprintTaskRepository.findAllBySprint(sprint).stream()
                // Select tasks that are not completed.
                .filter(task -> task.getTask().getState().equals(TaskState.TODO))
                // Select tasks that have no undone dependencies.
                .filter(task -> !taskService.taskHasUndoneDependencies(task.getTask()))
                // Sort tasks by priority, descending order.
                .sorted(Comparator.comparing(task -> task.getTask().getPriority()))
                // Sort tasks by deadline, ascending order.
                .sorted(Comparator.comparing(this::getTaskDuration))
                // Sort tasks by complexity, descending order.
                .sorted(Comparator.comparing(SprintTask::getSp).reversed())
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

    private AssignedTask assignTaskToTeamMember(TeamMember teamMember, SprintTask task) {
        AssignedTask assignedTask = new AssignedTask();
        assignedTask.setTask(task);
        assignedTask.setTeamMember(teamMember);
        assignedTaskRepository.save(assignedTask);
        return assignedTask;
    }

    private Boolean taskHasRequirementsForRole(SprintTask task) {
        return !sprintTaskRequiredRoleRepository.findByTask(task).isEmpty();
    }

    private Duration getTaskDuration(SprintTask task) {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Deadline deadline = task.getTask().getDeadline();

        if (deadline == null) {
            Sprint sprint = task.getSprint();

            // If there is no explicit deadline, we make task high priority.
            if (sprint.getEndTime() == null) {
                return Duration.ZERO;
            }

            return Duration.ofMillis(sprint.getEndTime().getTime() - current.getTime());
        }

        Timestamp end = deadline.getTime();
        return Duration.ofMillis(end.getTime() - current.getTime());
    }
}
