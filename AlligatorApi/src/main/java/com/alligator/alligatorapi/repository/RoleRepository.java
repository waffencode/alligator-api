package com.alligator.alligatorapi.repository;

import com.alligator.alligatorapi.entity.Role;
import com.alligator.alligatorapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByUser(User user);
}