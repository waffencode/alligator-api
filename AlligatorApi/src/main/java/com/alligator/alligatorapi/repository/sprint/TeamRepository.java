package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}