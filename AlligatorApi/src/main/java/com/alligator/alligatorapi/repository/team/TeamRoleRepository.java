package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.team.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('TEAM_LEAD')")
public interface TeamRoleRepository extends JpaRepository<TeamRole, Long> {
}