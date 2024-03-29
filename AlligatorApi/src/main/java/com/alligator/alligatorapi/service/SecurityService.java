package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.configuration.security.AuthenticationUserDetails;
import com.alligator.alligatorapi.entity.enums.RoleName;
import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.team.Team;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class SecurityService {
    public Boolean isUsernameSameAsPrincipal(String name) throws AccessDeniedException {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();

        return name.equals(principalName);
    }

    public Boolean isPrincipalIsTeamLeadOfTeam(Team team) throws AccessDeniedException {
        Long userId = getPrincipalId();

        Logger.getLogger(SecurityService.class.getName()).info("Validating user with id " + userId);

        return !Objects.equals(team.getTeamLead().getId(), userId);
    }

    public Boolean isPrincipalIsScrumMasterOfSprint(Sprint sprint) throws AccessDeniedException {
        Long userId = getPrincipalId();

        return Objects.equals(sprint.getScrumMaster().getId(), userId);
    }


    public Long getPrincipalId() throws AccessDeniedException {
        Object principalDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(principalDetails instanceof AuthenticationUserDetails authenticationUserDetails) {
            return authenticationUserDetails.getId();
        } else {
            throw new AccessDeniedException("Failed to parse id from principal details.");
        }
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
