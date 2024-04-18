package com.alligator.alligatorapi.model.repository.user;

import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(RoleName roleName);

    Role findByName(RoleName roleName);
}