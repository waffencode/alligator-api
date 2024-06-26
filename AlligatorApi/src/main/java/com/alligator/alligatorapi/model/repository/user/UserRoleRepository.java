package com.alligator.alligatorapi.model.repository.user;

import com.alligator.alligatorapi.model.entity.user.Role;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {
    <S extends UserRole> List<S> findAllByUser(User user);

    boolean existsByUserAndRole(User admin, Role roleAdmin);
}