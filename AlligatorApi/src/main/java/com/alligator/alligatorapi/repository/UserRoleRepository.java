package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}