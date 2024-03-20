package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.TeamMemberRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRolesRepository extends JpaRepository<TeamMemberRoles, Long> {
}