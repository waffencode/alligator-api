package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.SprintTaskRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintTaskRequiredRoleRepository extends JpaRepository<SprintTaskRole, Long> { }