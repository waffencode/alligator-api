package com.alligator.alligatorapi.model.repository.team;

import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Boolean existsByTeamAndUser(Team team, User user);

    Optional<TeamMember> findByUserId(Long user_id);
}