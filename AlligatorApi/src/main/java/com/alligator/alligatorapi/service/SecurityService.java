package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.configuration.security.AuthenticationUserDetails;
import com.alligator.alligatorapi.entity.enums.RoleNames;
import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.Team;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.*;
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

    public Boolean isPrincipalIsTeamLeadOfTeam(Team team) throws AccessDeniedException {
        Long userId = getPrincipalId();

        Logger.getLogger(SecurityService.class.getName()).info("Validating user with id " + userId);

        return !Objects.equals(team.getTeamLead().getId(), userId);
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

    public Boolean hasRole(RoleNames role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority authority : auth.getAuthorities()) {
            if(authority.getAuthority().equals(role.name())) return true;
        }

        return false;
    }

}
