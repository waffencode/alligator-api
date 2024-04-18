package com.alligator.alligatorapi.model.repository.team;

import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRoleRepository extends JpaRepository<TeamMemberRole, Long> {
    boolean existsByTeamMemberAndRole(TeamMember member, TeamRole role);
}