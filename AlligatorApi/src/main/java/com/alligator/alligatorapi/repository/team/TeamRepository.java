package com.alligator.alligatorapi.repository.team;

import com.alligator.alligatorapi.entity.sprint.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> { }