package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}