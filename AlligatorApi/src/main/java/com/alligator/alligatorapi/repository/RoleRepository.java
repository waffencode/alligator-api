package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}