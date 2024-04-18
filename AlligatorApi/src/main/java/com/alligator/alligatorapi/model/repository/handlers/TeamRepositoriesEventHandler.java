package com.alligator.alligatorapi.model.repository.handlers;

import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class TeamRepositoriesEventHandler {
    private final SecurityService securityService;

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeam(Team team) throws AccessDeniedException {
        if(!securityService.hasRole(RoleName.PROJECT_MANAGER))
            throw new AccessDeniedException("PROJECT_MANAGER role required");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeamRole(TeamRole teamRole) throws AccessDeniedException {
        if(!securityService.hasAnyRole(RoleName.BUSINESS_ANALYTIC, RoleName.PROJECT_MANAGER) )
            throw new AccessDeniedException("To manage team roles required role BUSINESS_ANALYTIC or PROJECT_MANAGER");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeamMemberRole(TeamMemberRole teamMemberRole) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(teamMemberRole.getTeamMember().getTeam()))
            throw new AccessDeniedException("To manage team roles required to be team lead of team " + teamMemberRole.getTeamMember().getTeam().getName());
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeamMember(TeamMember teamMember) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(teamMember.getTeam()))
            throw new AccessDeniedException("To manage team members required to be team lead of team " + teamMember.getTeam().getName());
    }
}
