package com.alligator.alligatorapi.model.repository.handlers;

import com.alligator.alligatorapi.model.entity.enums.TaskState;
import com.alligator.alligatorapi.model.entity.enums.TaskSwapRequestState;
import com.alligator.alligatorapi.model.entity.sprint.*;
import com.alligator.alligatorapi.service.SecurityService;
import com.alligator.alligatorapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;


@RepositoryEventHandler
@RequiredArgsConstructor
@Component
public class SprintRepositoriesEventHandler {
    private final SecurityService securityService;
    private final SprintService sprintService;

    @HandleBeforeDelete
    public void handleSprintDelete(Sprint sprint) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(sprint.getTeam()))
            throw new AccessDeniedException(STR . "Only team lead of team \{sprint.getTeam().getName()} can delete sprints");
    }

    @HandleBeforeCreate
    public void handleSprintCreate(Sprint sprint) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(sprint.getTeam()))
            throw new AccessDeniedException(STR . "Only team lead of team \{sprint.getTeam().getName()} can create/delete sprints");

        sprintService.validateSprintSave(sprint);
    }

    @HandleBeforeSave
    public void handleSprintSave(Sprint sprint) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(sprint.getTeam()) &&
                securityService.isPrincipalIsScrumMasterOfSprint(sprint))
            throw new AccessDeniedException(STR . "Only team lead of team \{sprint.getTeam().getName()} or scrum-master (User \{sprint.getScrumMaster().getUser().getUsername()}) can manage sprint");
    }

    @HandleBeforeCreate
    @HandleBeforeDelete
    @HandleBeforeSave
    public void handleSprintTask(SprintTask sprintTask) throws AccessDeniedException {
        if(!securityService.isPrincipalIsScrumMasterOfSprint(sprintTask.getSprint()))
            throw new AccessDeniedException(STR . "Only scrum-master (User \{sprintTask.getSprint().getScrumMaster().getUser().getUsername()}) can manage sprint tasks");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleSprintTaskRole(SprintTaskRole sprintTaskRole) throws AccessDeniedException {
        if(!securityService.isPrincipalIsScrumMasterOfSprint(sprintTaskRole.getTask().getSprint()))
            throw new AccessDeniedException(STR . "Only scrum-master (User \{sprintTaskRole.getTask().getSprint().getScrumMaster().getUser().getUsername()}) can manage sprint tasks");
    }

    public void handleAssignedTask(AssignedTask assignedTask) throws AccessDeniedException {
        // TODO: think how that will work!
    }

    /**
     * For successful creation of request, the following conditions must be met: <br>
     * - Task 1 was assigned to user <br>
     * - tasks can be swapped
     * - Task 1 and task 2 both belong to one sprint <br>
     * - Task 1 and task 2 both must be in states ТODO, PICKED or IN_PROGRESS <br>
     * @param taskSwapRequest {@link TaskSwapRequest}
     * @throws AccessDeniedException - thrown if conditions wasn't met
     */
    @HandleBeforeCreate
    public void handleTaskSwapRequestCreate(TaskSwapRequest taskSwapRequest) throws AccessDeniedException {
        if(taskSwapRequest.getState() != TaskSwapRequestState.IN_QUESTION)
            throw new AccessDeniedException("Task swap requests can not be created with any other state except IN_QUESTION");

        if(!securityService.isTaskAssignedToPrincipal(taskSwapRequest.getTask1()))
            throw new AccessDeniedException("Task to swap is not assigned for user in the first place");

        if(!taskSwapRequest.getTask1().getSprint().equals(taskSwapRequest.getTask2().getSprint()))
            throw new org.springframework.security.access.AccessDeniedException("Tasks belong to different sprints");

        TaskState task1State = taskSwapRequest.getTask1().getTask().getState();
        TaskState task2State = taskSwapRequest.getTask2().getTask().getState();
        if (task1State != TaskState.TODO && task1State != TaskState.PICKED && task1State != TaskState.IN_PROGRESS)
            throw new org.springframework.security.access.AccessDeniedException(STR . "Task 1 state \{task1State} not allowing swaps. (Desired states: ТODO, PICKED or IN_PROGRESS)");

        if (task2State != TaskState.TODO && task2State != TaskState.PICKED && task2State != TaskState.IN_PROGRESS)
            throw new org.springframework.security.access.AccessDeniedException(STR . "Task 1 state \{task2State} not allowing swaps. (Desired states: ТODO, PICKED or IN_PROGRESS)");
    }

    @HandleBeforeSave
    public void handleTaskSwapRequestSave(TaskSwapRequest taskSwapRequest) throws AccessDeniedException {
        // TODO: think how that will work!
    }

    /**
     * Conditions for task swap request deleting: <br>
     * - Swap request state is IN_QUESTION <br>
     * - Task 1 is assigned to user <br>
     * @param taskSwapRequest {@link TaskSwapRequest}
     * @throws AccessDeniedException - thrown if conditions wasn't met
     */
    @HandleBeforeDelete
    public void handleTaskSwapRequestDelete(TaskSwapRequest taskSwapRequest) throws AccessDeniedException {
        if(!taskSwapRequest.getState().equals(TaskSwapRequestState.IN_QUESTION))
            throw new AccessDeniedException("Task swap request is processed already");

        if(!securityService.isTaskAssignedToPrincipal(taskSwapRequest.getTask1()))
            throw new AccessDeniedException("Task to swap is not assigned for user in the first place");
    }
}
