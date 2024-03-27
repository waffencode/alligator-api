package com.alligator.alligatorapi.repository.user;

import com.alligator.alligatorapi.entity.enums.RoleName;
import com.alligator.alligatorapi.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(RoleName roleName);

    Role findByName(RoleName roleName);
}