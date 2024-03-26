package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.configuration.security.AuthenticationUserDetails;
import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.Team;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class SecurityService {
    public Boolean validateUsernameSameAsPrincipal(String name) throws AccessDeniedException {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!name.equals(principalName))
            throw new AccessDeniedException("Name " + name + " failed validation for rights check on this method");

        return true;
    }

    public Boolean validatePrincipalIsTeamLeadOfTeam(Team team) throws AccessDeniedException {
        Long userId = getPrincipalId();

        Logger.getLogger(SecurityService.class.getName()).info("Validating user with id " + userId);

        if(!Objects.equals(team.getTeamLead().getId(), userId))
            throw new AccessDeniedException("User is not team lead of team " + team.getName());

        return true;
    }

    public Boolean validatePrincipalIsScrumMasterOfSprint(Sprint sprint) throws AccessDeniedException {
        Long userId = getPrincipalId();

        if(!Objects.equals(sprint.getScrumMaster().getId(), userId))
            throw new AccessDeniedException("User is not scrum-master of sprint id == " + sprint.getId());

        return true;
    }

    public Long getPrincipalId() throws AccessDeniedException {
        Object principalDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(principalDetails instanceof AuthenticationUserDetails authenticationUserDetails) {
            return authenticationUserDetails.getId();
        } else {
            throw new AccessDeniedException("Failed to parse id from principal details.");
        }
    }
}
