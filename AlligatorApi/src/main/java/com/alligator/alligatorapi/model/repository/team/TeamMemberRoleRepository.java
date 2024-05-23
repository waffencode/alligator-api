package com.alligator.alligatorapi.model.repository.team;

import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TeamMemberRoleRepository extends JpaRepository<TeamMemberRole, Long>, QuerydslPredicateExecutor<TeamMemberRole> {
    boolean existsByTeamMemberAndRole(TeamMember member, TeamRole role);
}