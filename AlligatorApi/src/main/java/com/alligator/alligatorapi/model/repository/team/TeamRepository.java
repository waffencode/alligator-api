package com.alligator.alligatorapi.model.repository.team;

import com.alligator.alligatorapi.model.entity.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> { }