package com.alligator.alligatorapi.configuration.repository;

import com.alligator.alligatorapi.entity.enums.RoleNames;
import com.alligator.alligatorapi.entity.sprint.Team;
import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class TeamRepositoriesEventsHandler {
    private final SecurityService securityService;

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    @HandleBeforeLinkDelete
    @HandleBeforeLinkSave
    public void handleTeamCreate(Team team) throws AccessDeniedException {
        if(!securityService.hasRole(RoleNames.ADMIN))
            throw new AccessDeniedException("Admin role required");
    }
}
