package com.alligator.alligatorapi.configuration.repository;

import com.alligator.alligatorapi.entity.enums.RoleName;
import com.alligator.alligatorapi.entity.team.Team;
import com.alligator.alligatorapi.entity.team.TeamMember;
import com.alligator.alligatorapi.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.entity.team.TeamRole;
import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

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
    public void handleTeam(Team team) throws AccessDeniedException {
        if(!securityService.hasRole(RoleName.PROJECT_MANAGER))
            throw new AccessDeniedException("PROJECT_MANAGER role required");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    @HandleBeforeLinkDelete
    @HandleBeforeLinkSave
    public void handleTeamRole(TeamRole teamRole) throws AccessDeniedException {
        if(!securityService.hasAnyRole(RoleName.BUSINESS_ANALYTIC, RoleName.PROJECT_MANAGER) )
            throw new AccessDeniedException("To manage team roles required role BUSINESS_ANALYTIC or PROJECT_MANAGER");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    @HandleBeforeLinkDelete
    @HandleBeforeLinkSave
    public void handleTeamMemberRole(TeamMemberRole teamMemberRole) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(teamMemberRole.getTeamMember().getTeam()))
            throw new AccessDeniedException("To manage team roles required to be team lead of team");
    }

    @HandleBeforeCreate
    @HandleBeforeDelete
    @HandleBeforeLinkDelete
    public void handleTeamMemberCreateAndDelete(TeamMember teamMember) throws AccessDeniedException {
        if(!securityService.isPrincipalIsTeamLeadOfTeam(teamMember.getTeam()))
            throw new AccessDeniedException("To manage team members required to be team lead of team");
    }

    /*
    Обработка изменения (UserState)TeamMember.state:
    Состояние может измениться в двух случаях:
    1) его изменяет PROJECT_MANAGER
        - INACTIVE -> ACTIVE - ни на что не влияет
        - ACTIVE -> INACTIVE - нужно сделать пользователя неактивным во всех командах
    2) его изменяет тимлид
        - INACTIVE -> ACTIVE - если пользователь был INACTIVE, делаем ACTIVE
        - ACTIVE -> INACTIVE - ни на что не влияет

    С другой стороны может ну его нахуй. Просто Убрать состояние юзера как таковое, и оставить только непосредственно
    состояние члена команды. Завтра думать буду

     */
    public void handleTeamMemberSave(TeamMember teamMember) throws AccessDeniedException {

    }
}
