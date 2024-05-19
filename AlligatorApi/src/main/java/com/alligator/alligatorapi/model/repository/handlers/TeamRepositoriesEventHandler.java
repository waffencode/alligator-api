package com.alligator.alligatorapi.model.repository.handlers;

import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.entity.enums.TeamMemberState;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRepository;
import com.alligator.alligatorapi.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
@Slf4j
public class TeamRepositoriesEventHandler {
    private final SecurityService securityService;
    private final TeamMemberRepository teamMemberRepository;

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    @Operation(summary = "Создание новой команды.",
            description = "Доступно только пользователям с ролью PROJECT_MANAGER",
            security = @SecurityRequirement(name = "bearerAuth"))
    public void handleTeam(Team team) throws AccessDeniedException {
        if (!securityService.hasRole(RoleName.PROJECT_MANAGER))
            throw new AccessDeniedException("PROJECT_MANAGER role required");
        }

    /**
     * When creating a team, we automatically add the team lead as a team member.
     * @param team {@link Team}
     */
    @HandleAfterCreate
    public void handleAfterTeamCreate(Team team) {
        if (team.getTeamLead() != null) {
            TeamMember teamLead = new TeamMember();
            teamLead.setTeam(team);
            teamLead.setUser(team.getTeamLead());
            teamLead.setState(TeamMemberState.ACTIVE);

            log.info("Adding team lead {} [{}] to team {} [{}] on creation", team.getTeamLead().getUsername(), team.getTeamLead().getId(), team.getName(), team.getId());
            teamMemberRepository.save(teamLead);
        }
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeamRole(TeamRole teamRole) throws AccessDeniedException {
        if (!securityService.hasAnyRole(RoleName.BUSINESS_ANALYTIC, RoleName.PROJECT_MANAGER) )
            throw new AccessDeniedException("To manage team roles required role BUSINESS_ANALYTIC or PROJECT_MANAGER");
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeamMemberRole(TeamMemberRole teamMemberRole) throws AccessDeniedException {
        if (!securityService.isPrincipalIsTeamLeadOfTeam(teamMemberRole.getTeamMember().getTeam()))
            throw new AccessDeniedException("To manage team roles required to be team lead of team " + teamMemberRole.getTeamMember().getTeam().getName());
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    public void handleTeamMember(TeamMember teamMember) throws AccessDeniedException {
        if (!securityService.isPrincipalIsTeamLeadOfTeam(teamMember.getTeam()))
            throw new AccessDeniedException("To manage team members required to be team lead of team " + teamMember.getTeam().getName());
    }
}
