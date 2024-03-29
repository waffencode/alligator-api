package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.team.TeamMember;
import com.alligator.alligatorapi.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.entity.team.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRoleRepository extends JpaRepository<TeamMemberRole, Long> {
    boolean existsByTeamMemberAndRole(TeamMember member, TeamRole role);
}