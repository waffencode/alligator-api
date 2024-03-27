package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> { }