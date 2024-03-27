package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.team.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}