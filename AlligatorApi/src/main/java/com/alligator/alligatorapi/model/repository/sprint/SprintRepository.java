package com.alligator.alligatorapi.model.repository.sprint;

import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<TeamMember> findAll(Sprint sprint);
}