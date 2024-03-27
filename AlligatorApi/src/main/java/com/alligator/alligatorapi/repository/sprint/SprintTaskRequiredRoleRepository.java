package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.SprintTaskRequiredRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SprintTaskRequiredRoleRepository extends JpaRepository<SprintTaskRequiredRole, Long> { }