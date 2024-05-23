package com.alligator.alligatorapi.model.repository.team;

import com.alligator.alligatorapi.model.entity.team.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('TEAM_LEAD')")
public interface TeamRoleRepository extends JpaRepository<TeamRole, Long>, QuerydslPredicateExecutor<TeamRole> {
}