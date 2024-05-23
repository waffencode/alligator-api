package com.alligator.alligatorapi.model.repository.sprint;

import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long>, QuerydslPredicateExecutor<Sprint> {
    List<Sprint> findAllByTeam(Team team);
}