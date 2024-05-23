package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.configuration.security.AuthenticationUserDetails;
import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.repository.sprint.AssignedTaskRepository;
import com.alligator.alligatorapi.model.repository.team.TeamMemberRepository;
import com.alligator.alligatorapi.model.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final AssignedTaskRepository assignedTaskRepository;

    public Boolean isUsernameSameAsPrincipal(String name) {
        String principalName = getPrincipalUsername();

        return name.equals(principalName);
    }

    public Boolean isPrincipalIsTeamLeadOfTeam(Team team) {
        Long userId = getPrincipalId();

        log.info("Checking if the user with ID {} is the team lead of team {} (comparing with ID {}).", userId, team.getName(), team.getTeamLead().getId());
        return Objects.equals(team.getTeamLead().getId(), userId);
    }

    public Boolean isPrincipalIsScrumMasterOfSprint(Sprint sprint) {
        Long userId = getPrincipalId();

        return Objects.equals(sprint.getScrumMaster().getId(), userId);
    }

    public Boolean isPrincipalTeamMemberOfTeam(Team team) {
        Optional<User> principalUser = userRepository.findByUsername(getPrincipalUsername());

        if(principalUser.isEmpty())
            throw new AccessDeniedException(STR . "User \{getPrincipalUsername()} is authenticated successful, but could be not found by username (WTF?????? Tell Ване)");

        return teamMemberRepository.existsByTeamAndUser(team, principalUser.get());
    }

    public Boolean isTaskAssignedToPrincipal(SprintTask sprintTask) {
        Optional<TeamMember> teamMemberMappedEntity = teamMemberRepository.findByUserIdAndTeam(getPrincipalId(), sprintTask.getSprint().getTeam());

        //noinspection OptionalIsPresent
        if(teamMemberMappedEntity.isEmpty())
            return false;

        return assignedTaskRepository.existsByTaskAndTeamMember(sprintTask.getTask(), teamMemberMappedEntity.get());
    }

    public Long getPrincipalId() throws AuthenticationServiceException {
        Object principalDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(principalDetails instanceof AuthenticationUserDetails authenticationUserDetails) {
            return authenticationUserDetails.getId();
        } else {
            throw new AuthenticationServiceException("Failed to parse id from principal details.");
        }
    }

    public String getPrincipalUsername() throws AuthenticationServiceException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(username == null)
            throw new AuthenticationServiceException("Authentication exception: username is null!");

        return username;
    }

    public Boolean hasRole(RoleName role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority authority : auth.getAuthorities()) {
            if(authority.getAuthority().equals(role.name())) return true;
        }

        return false;
    }

    public Boolean hasAnyRole(RoleName... roles) {
        for (RoleName roleName : roles) {
            if(hasRole(roleName)) return true;
        }

        return false;
    }

}
