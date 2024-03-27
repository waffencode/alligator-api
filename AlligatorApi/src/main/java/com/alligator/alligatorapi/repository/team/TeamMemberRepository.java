package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.sprint.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}