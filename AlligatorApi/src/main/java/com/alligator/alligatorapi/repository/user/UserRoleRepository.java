package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.sprint.TeamMemberRole;
import com.alligator.alligatorapi.entity.user.Role;
import com.alligator.alligatorapi.entity.user.User;
import com.alligator.alligatorapi.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @PreAuthorize("hasRole('ADMIN')")
    <S extends UserRole> S save(S UserRole);

    @PreAuthorize("false")
    <S extends UserRole> List<S> saveAll(Iterable<S> UserRole);

    <S extends UserRole> List<S> findAllByUser(User user);

    boolean existsByUserAndRole(User admin, Role roleAdmin);
}