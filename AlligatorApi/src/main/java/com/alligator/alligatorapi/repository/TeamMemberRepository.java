package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}