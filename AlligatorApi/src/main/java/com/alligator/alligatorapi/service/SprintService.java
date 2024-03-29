package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.sprint.SprintTask;
import com.alligator.alligatorapi.entity.sprint.SprintTaskRole;
import com.alligator.alligatorapi.entity.team.TeamMember;
import com.alligator.alligatorapi.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.entity.team.TeamRole;
import com.alligator.alligatorapi.repository.sprint.SprintTaskRequiredRoleRepository;
import com.alligator.alligatorapi.repository.team.TeamMemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final TeamMemberRoleRepository teamMemberRoleRepository;
    private final SprintTaskRequiredRoleRepository sprintTaskRequiredRoleRepository;

    public Boolean teamMemberMeetsRequiredRolesForTask(TeamMember member, SprintTask task) {
        List<TeamRole> requiredRoles = sprintTaskRequiredRoleRepository.findByTask(task).stream()
                .map(SprintTaskRole::getRole).toList();

        for(TeamRole role : requiredRoles) {
            if(!teamMemberRoleRepository.existsByTeamMemberAndRole(member, role))
                return false;
        }

        return true;
    }
}
