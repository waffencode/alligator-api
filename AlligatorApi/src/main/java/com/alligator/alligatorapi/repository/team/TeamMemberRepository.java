package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.team.Team;
import com.alligator.alligatorapi.entity.team.TeamMember;
import com.alligator.alligatorapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Boolean existsByTeamAndUser(Team team, User user);

    Optional<TeamMember> findByUserId(Long user_id);
}