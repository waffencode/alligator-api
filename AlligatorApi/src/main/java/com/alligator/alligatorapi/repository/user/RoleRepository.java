package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.user.Role;
import com.alligator.alligatorapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public interface RoleRepository extends JpaRepository<Role, Long> {
    @PreAuthorize("isAuthenticated()")
    List<Role> findAllByUser(User user);
}