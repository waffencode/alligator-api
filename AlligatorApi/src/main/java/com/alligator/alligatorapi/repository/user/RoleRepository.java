package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.enums.RoleNames;
import com.alligator.alligatorapi.entity.user.Role;
import com.alligator.alligatorapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @RestResource(exported = false)
    <S extends Role> S save(S role);
    @RestResource(exported = false)

    <S extends Role> List<S> saveAll(Iterable<S> roles);

    boolean existsByName(RoleNames roleName);

    Role findByName(RoleNames roleNames);
}