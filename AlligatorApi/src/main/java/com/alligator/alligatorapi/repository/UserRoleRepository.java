package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.User;
import com.alligator.alligatorapi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUser(User user);
}