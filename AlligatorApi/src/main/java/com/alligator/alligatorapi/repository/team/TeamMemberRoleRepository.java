package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.sprint.TeamMember;
import com.alligator.alligatorapi.entity.sprint.TeamMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TeamMemberRoleRepository extends JpaRepository<TeamMemberRole, Long> { }