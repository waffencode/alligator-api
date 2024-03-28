package com.alligator.alligatorapi.configuration.repository;

import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.SprintTask;
import com.alligator.alligatorapi.entity.sprint.SprintTaskRole;
import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;


@RepositoryEventHandler
@RequiredArgsConstructor
@Component
public class SprintRepositoriesEventsHandler {
    private final SecurityService securityService;

    @HandleBeforeCreate
    @HandleBeforeDelete
    public void handleSprintCreateDelete(Sprint sprint) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(sprint.getTeam()))
            throw new AccessDeniedException("Only team lead of team " + sprint.getTeam().getName() + " can create/delete sprints");
    }

    @HandleBeforeSave
    public void handleSprintSave(Sprint sprint) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(sprint.getTeam()) &&
                securityService.isPrincipalIsScrumMasterOfSprint(sprint))
            throw new AccessDeniedException(STR . "Only team leaed of team \{sprint.getTeam().getName()} or scrum-master (User \{sprint.getScrumMaster().getUser().getUsername()}) can manage sprint");
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
}
