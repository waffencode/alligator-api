package com.alligator.alligatorapi.model.repository.team;

import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TeamRepository extends JpaRepository<Team, Long> , QuerydslPredicateExecutor<Team> { }