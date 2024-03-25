package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}